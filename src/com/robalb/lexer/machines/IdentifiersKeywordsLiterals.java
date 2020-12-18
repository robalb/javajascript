package com.robalb.lexer.machines;

import com.robalb.Token;

/**
 * @see <a href="https://www.ecma-international.org/ecma-262/#sec-tokens">ECMAscript reference</a>
 * @see <a href="https://unicode.org/reports/tr31/">Unicode standard annex #31</a>
 * https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html#isUnicodeIdentifierStart-int-
 */
public class IdentifiersKeywordsLiterals implements Machine{

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
    }

    @Override
    public int step(int intC) throws IllegalStateException {
        if(state == States._END){
            throw new IllegalStateException("cannot use step after the machine reaches the _END state");
        }

        //when going into escapesequence mode, set a variable to flag the previous state: identifierStart,
        //identifierPart, or string.
        //it is then up to the escapesequence machine logic to handle errors, or on success update the
        //identifier/string string and return to the correct machine state
        Character.isUnicodeIdentifierStart(intC);
        Character.isUnicodeIdentifierPart(intC);
        switch(state){
            case _START ->{
                if(intC == -1) state = States._END;
            }
            default -> throw new IllegalStateException("Unexpected value: " + state);
        }

        error = "invalid state";
        return Machine.ERROR;
    }

    public IdentifiersKeywordsLiterals(){
        reset();
    }

    @Override public void reset() {
        state = States._START;
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
