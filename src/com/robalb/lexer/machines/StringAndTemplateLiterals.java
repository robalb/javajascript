package com.robalb.lexer.machines;

import com.robalb.Token;
import com.robalb.Tokens;

public class StringAndTemplateLiterals implements Machine{

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
        STRING, BACKSLASH,
    }

    //variables used internally
    boolean singleTick;
    boolean allowLf;
    //TODO research and find best capacity
    StringBuilder string = new StringBuilder(200);
    StringBuilder escapeString = new StringBuilder();

    public StringAndTemplateLiterals(){
        reset();
    }

    @Override
    public void reset() {
        state = States._START;
        string.setLength(0);
        allowLf = false;
    }

    @Override
    public int step(int intC) throws IllegalStateException {
        if(state == States._END){
            throw new IllegalStateException("cannot use step after the machine reaches the _END state");
        }

        final boolean isNewline = intC == 0x00D || intC == 0x000A || intC == 0x2028 || intC == 0x2029;

        switch(state){
            case _START ->{
                if(intC == '\'' || intC == '\"'){
                    singleTick = intC == '\'';
                    state = States.STRING;
                    return Machine.STEPPING;
                }
                else{
                    state = States._END;
                    return Machine.NOMATCH;
                }
            }

            case STRING -> {
                if((isNewline && !(allowLf && intC == 0x000A)) || intC == -1){
                    error = "unterminated string literal";
                    return Machine.ERROR;
                }
                else if(intC == '\\'){
                    state = States.BACKSLASH;
                }
                else if((intC == '\'' && singleTick) || (intC == '\"' && !singleTick)){
                    state = States._END;
                    return processString();
                }
                else{
                    string.appendCodePoint(intC);
                }
                allowLf = false;
                return Machine.STEPPING;
            }

            case BACKSLASH -> {
                //if <cr>, set flag to allow <lf> if it is the next character in the string
                if(intC == 0x000D){
                    allowLf = true;
                }
                //TODO
                //newline sequence, escape sequences
                if(intC == -1){
                    error = "unterminated string literal";
                    return Machine.ERROR;
                }
                else if(intC == 'u'){
                    //TODO
                    escapeString.setLength(0);
                    escapeString.appendCodePoint('u');
                    //add this u to the escape stringbuilder
//                    state = States.ESCAPE_1;//in this state, don't forget to check for EOF
                    return Machine.STEPPING;
                }
                //TODO is this good? does this justify a hashmap?
                else if(intC == 'b') string.appendCodePoint(0x0008);
                else if(intC == 't') string.appendCodePoint(0x0009);
                else if(intC == 'n') string.appendCodePoint(0x000A);
                else if(intC == 'v') string.appendCodePoint(0x000B);
                else if(intC == 'f') string.appendCodePoint(0x000C);
                else if(intC == 'r') string.appendCodePoint(0x000D);
                //this covers everything else, included \' \" \\ and newlines != cr-lf
                else string.appendCodePoint(intC);
                state = States.STRING;
                return Machine.STEPPING;
            }

            default -> throw new IllegalStateException("Unexpected value: " + state);
        }

//        error = "invalid tokenizer state";
//        return Machine.ERROR;
    }

    private int processString() {
        //TODO
        token = new Token(Tokens.L_STRING, string.toString());
        return Machine.PERFECTMATCH;
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
