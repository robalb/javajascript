package com.robalb.lexer.machines;

import com.robalb.Token;
import com.robalb.Tokens;

/**
 * machine for all the tokens that are of type Token.TV_IGNORE:
 */
public class IgnoreType implements Machine{

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

        SPACE,
        CR,
        SLASH,
        MULTILINE_COMMENT,
        MULTILINE_COMMENT_1,
        INLINE_COMMENT

    }

    //varilable used internally
    private boolean lineTerminatorsContained;

    @Override
    public int step(int intC) throws IllegalStateException {
        if(state == States._END){
            throw new IllegalStateException("cannot use step after the machine reaches the _END state");
        }

        //by ECMA standards, any codepoint int the “Space_Separator” (“Zs”) category or one of u0009 u000b u000c uFEFF
        final boolean isSpace = intC != -1 && (
                intC == 0x0009 || intC == 0x000b || intC == 0x000c || intC == 0xFEFF ||
                Character.getType(intC) == Character.SPACE_SEPARATOR
                );

        final boolean isNewline = !isSpace && ( intC == 0x00D ||
                        intC == 0x000A || intC == 0x2028 || intC == 0x2029
        );

        switch(state){
            case _START -> {
                //eof case
                if(intC == -1){
                    state = States._END;
                    return Machine.NOMATCH;
                }
                //slash -beginnig of a comment - case
                if(intC == '/'){
                    state = States.SLASH;
                    return Machine.STEPPING;
                }
                //space - beginning of a speparator case
                if(isSpace){
                    state = States.SPACE;
                    return Machine.STEPPING;
                }
                //line end, but only <cr>. For line reporting purposes, <cr><lf> are considered a as a single linefeed token
                if(intC == 0x000D){
                    state = States.CR;
                    return Machine.STEPPING;
                }
                //all the others line end
                else if(isNewline){
                    state = States._END;
                    token = new Token(Tokens.WHITE_SPACE);
                    return Machine.ENDMATCH;
                }
                //no match
                else{
                    state = States._END;
                    return Machine.NOMATCH;
                }

            }

            case SLASH ->{
                if( intC == '/'){
                    state = States.INLINE_COMMENT;
                }
                else if( intC == '*'){
                    state = States.MULTILINE_COMMENT;
                }
                return Machine.STEPPING;
            }

            case INLINE_COMMENT -> {
                if(intC == -1){
                    state = States._END;
                    token = new Token(Tokens.COMMENT);
                    return Machine.PERFECTMATCH;
                }
                if(isNewline){
                    state = States._END;
                    token = new Token(Tokens.COMMENT);
                    return Machine.ENDMATCH;
                }
                return Machine.STEPPING;
            }

            case MULTILINE_COMMENT -> {
                if(intC == -1){
                    state = States._END;
                    error = "unterminated comment";
                    return Machine.ERROR;
                }
                if( intC == '*'){
                    state = States.MULTILINE_COMMENT_1;
                }
                //if there is one or more line terminator, set a flag to true
                //since we are not counting the line terminators, there is no need for cr lf detection logic
                if(isNewline){
                    lineTerminatorsContained = true;
                }

                return Machine.STEPPING;

            }

            case MULTILINE_COMMENT_1 -> {
                if(intC == -1){
                    state = States._END;
                    error = "unterminated comment";
                    return Machine.ERROR;
                }
                if( intC == '/'){
                    state = States._END;
                    token = lineTerminatorsContained?
                            new Token(Tokens.MUTLILINE_COMMENT) :
                            new Token(Tokens.COMMENT);
                    return Machine.PERFECTMATCH;
                }
                state = States.MULTILINE_COMMENT;
                return Machine.STEPPING;
            }

            case SPACE ->{
                if(isSpace){
                    return Machine.STEPPING;
                }else{
                    state = States._END;
                    token = new Token(Tokens.WHITE_SPACE);
                    return Machine.ENDMATCH;
                }
            }

            case CR ->{
                state = States._END;
                token = new Token(Tokens.LINE_TERMINATOR);
                if(intC == 0x000A){
                    //a line terminator composed by the seq. <cr><lf>
                    return Machine.PERFECTMATCH;
                }
                //the current char is not part of the line terminator
                return Machine.ENDMATCH;
            }

            default -> throw new IllegalStateException("Unexpected value: " + state);
        }

    }

    public IgnoreType(){
        reset();
    }


    @Override public void reset() {
        state = States._START;
        lineTerminatorsContained = false;
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
