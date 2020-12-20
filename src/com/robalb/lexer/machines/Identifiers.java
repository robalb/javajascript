package com.robalb.lexer.machines;

import com.robalb.Token;
import com.robalb.Tokens;


/**
 * machine for all the identifiers and the identifier sublcasses such as tokens and reserved words
 *
 * @see <a href="https://www.ecma-international.org/ecma-262/#sec-tokens">ECMAscript reference</a>
 * @see <a href="https://unicode.org/reports/tr31/">Unicode standard annex #31</a>
 * https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html#isUnicodeIdentifierStart-int-
 */
public class Identifiers implements Machine{

    String error = "";
    Token token;

    /**
     * the current internal state
     */
    protected States state;
    /**
     * all the internal states
     */
    private enum States {
        _START,
        _END,

        ID_PART,
        ESCAPE, ESCAPE_1, ESCAPE_2, ESCAPE_3,
    }

    //internally used variables
    private enum EscapeEntryPositions {
        ID_START,
        ID_PART
    }
    EscapeEntryPositions entryPosition;
    //40 is the average max length of a js identifier (data taken from an analisis of the top 100 js projects)
    StringBuilder identifierValue = new StringBuilder(40);
    StringBuilder escapeValue = new StringBuilder();
    boolean hadEscape;


    @Override
    public int step(int intC) throws IllegalStateException {
        if(state == States._END){
            throw new IllegalStateException("cannot use step after the machine reaches the _END state");
        }

        //hex digit: a char in the range 0-9 a-z A-Z
        final boolean isHex = ( intC >= '0' && intC <= '9' ) || ( intC >= 'a' && intC <= 'f' ) || ( intC >= 'A' && intC <= 'F' );

        //when going into escapesequence mode, set a variable to flag the previous state: identifierStart,
        //identifierPart, or string.
        //it is then up to the escapesequence machine logic to handle errors, or on success update the
        //identifier/string string and return to the correct machine state

        //when returning the token result, check against the reserved keywords list. if there is a match, but
        // the escapeSequenceUsed flag is true,
        //throw error 'Escape sequence in keyword [keyword]'
        switch(state){
            case _START ->{
                //eof case
                if(intC == -1){
                    state = States._END;
                    return Machine.NOMATCH;
                }
                //identifierStart
                if(isIdStart(intC)){
                    //reset the stringBuilder
                    identifierValue.setLength(0);
                    identifierValue.appendCodePoint(intC);
                    state = States.ID_PART;
                    return Machine.STEPPING;
                }
                //\ (beginning of unicode escape sequence)
                if(intC == '\\'){
                    //reset the stringBuilder
                    identifierValue.setLength(0);
                    //enter escape mode
                    state = States.ESCAPE;
                    entryPosition = EscapeEntryPositions.ID_START;
                    return Machine.STEPPING;
                }
                //no match
                state = States._END;
                return Machine.NOMATCH;
            }

            case ID_PART -> {
                //identifierContinue
                if(isIdPart(intC)){
                    identifierValue.appendCodePoint(intC);
                    return Machine.STEPPING;
                }
                //beginning of escape sequence
                else if(intC == '\\'){
                    state = States.ESCAPE;
                    entryPosition = EscapeEntryPositions.ID_PART;
                    return Machine.STEPPING;
                }
                //eof or nomatch: ENDMATCH
                else{
                    state = States._END;
                    return processToken();
                }
            }

            case ESCAPE -> {
                if(intC == 'u'){
                    //this could be a working escape sequence, initialize its value buffer
                    escapeValue.setLength(0);
                    state = States.ESCAPE_1;
                    return Machine.STEPPING;
                }else{
                    error = "invalid escape sequence: expected 'u'";
                    return Machine.ERROR;
                }
            }

            case ESCAPE_1 -> {
                if(intC == '{'){
                    state = States.ESCAPE_2;
                    return Machine.STEPPING;
                }
                else if(isHex){
                    state = States.ESCAPE_3;
                    escapeValue.appendCodePoint(intC);
                    return Machine.STEPPING;
                }
                else{
                    error = "invalid escape sequence: expected '{' or hexDigit";
                    return Machine.ERROR;
                }
            }

            case ESCAPE_2 -> {
                if(isHex){
                    escapeValue.appendCodePoint(intC);
                    return Machine.STEPPING;
                }
                else if(intC == '}'){
                    //escape completed logic
                    return escapeCompleted(true);
                }
                else{
                    error = "invalid escape sequence: expected '}'";
                    return Machine.ERROR;
                }
            }

            case ESCAPE_3 -> {
                if(isHex){
                    escapeValue.appendCodePoint(intC);
                    if(escapeValue.length() == 4){
                        //escape completed logic
                        return escapeCompleted(false);
                    }
                    return Machine.STEPPING;
                }
                else{
                    error = "invalid escape sequence: expected 4 hex digits";
                    return Machine.ERROR;
                }
            }

            default -> throw new IllegalStateException("Unexpected value: " + state);
        }

    }

    private boolean isIdStart(int intC){
        return Character.isUnicodeIdentifierStart(intC) || intC == '$' || intC == '_';
    }

    private boolean isIdPart(int intC){
        return Character.isUnicodeIdentifierPart(intC) || intC == '$' || intC == 0x200C || intC == 0x200D;
    }

    private int escapeCompleted(boolean codepointEscape){
        //TODO find a way to avoid exception
        int escapeInt;
        try {
            escapeInt = Integer.parseUnsignedInt(escapeValue.toString(), 16);
        }catch(NumberFormatException e){
            error = "invalid escape sequence: value out of range";
            return Machine.ERROR;
        }
        //https://www.ecma-international.org/ecma-262/#prod-CodePoint
        if(codepointEscape && escapeInt > 0x10FFFF){
            error = "invalid escape sequence: value must be ≤ 0x10FFFF";
            return Machine.ERROR;
        }
        if( (entryPosition == EscapeEntryPositions.ID_START && !isIdStart(escapeInt)) ||
            (entryPosition == EscapeEntryPositions.ID_PART && !isIdPart(escapeInt)) ){
            error = "invalid escape sequence: codepoint can't be used in an identifier";
            return Machine.ERROR;
        }
        identifierValue.appendCodePoint(escapeInt);
        state = States.ID_PART;
        hadEscape = true;
        return Machine.STEPPING;
    }

    private int processToken(){
        //https://www.ecma-international.org/ecma-262/#sec-keywords-and-reserved-words
        //basically there are 5 categories of identifiers:
        //always allowed, never allowed, contextually allowed, contextually disallowed in strict mode, always allowed but act as keyword in particular situations
        boolean isKeyword = false;

        if(isKeyword){
            if(hadEscape){
                error = "Escape sequence in keyword ";
                return Machine.ERROR;
            }else{
                token = new Token(Tokens.K_ELSE);
            }
        }
        else{
            token = new Token(Tokens.IDENTIFIER, identifierValue.toString());
        }

        return Machine.ENDMATCH;
    }

    public Identifiers(){
        reset();
    }

    @Override public void reset() {
        state = States._START;
        hadEscape = false;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public Token getToken() {
        return token;
    }
}
