package com.robalb.lexer.machines;

import com.robalb.Tokens;

public class Operators_0 implements Machine{
    /**
     * all the internal states, with their associated public state
     */
    private enum states {
        INITIAL(Machine.STEPPING),
        PLUS(Machine.STEPPING),
        MINUS(Machine.STEPPING),
        MULT(Machine.STEPPING),
        REMAINDER(Machine.STEPPING),
        LESS(Machine.STEPPING),
        GREATER(Machine.STEPPING),
        EQUAL(Machine.STEPPING),
        NOT(Machine.STEPPING),
        AND(Machine.STEPPING),
        OR(Machine.STEPPING),
        XOR(Machine.STEPPING),


        //end states
        NOMATCH(Machine.NOMATCH),
        ERROR(Machine.ERROR),
        PERFECTMATCH(Machine.PERFECTMATCH),
        ENDMATCH(Machine.ENDMATCH);

        public int publicState;
        states(int publicState) {
            this.publicState = publicState;
        }
    }

    /**
     * initialization or reset
     */
    @Override public void reset() {
        this.error = null;
        this.token = null;
        this.state = states.INITIAL;
    }

    /**
     * core of the machine
     */
    @Override public int step(char c) {
        //TODO: this is very ripetitive stuff. find way to store it in a structure of some sort
        switch(this.state){
            case INITIAL:
                if(c == '+') this.state = states.PLUS;
                else if(c == '-') this.state = states.MINUS;
                else if(c == '*') this.state = states.MULT;
                else if(c == '%') this.state = states.REMAINDER;
                else if(c == '<') this.state = states.LESS;
                else if(c == '>') this.state = states.GREATER;
                else if(c == '=') this.state = states.EQUAL;
                else if(c == '!') this.state = states.NOT;
                else if(c == '&') this.state = states.AND;
                else if(c == '|') this.state = states.OR;
                else if(c == '^') this.state = states.XOR;
                else this.state = states.NOMATCH;
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_ADDITION_ASSIGN;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case MINUS:
                if(c == '-'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_DECREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_SUBTRACTION_ASSIGN;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_MINUS;
                }
                break;

            case MULT:
                if(c == '*'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_EXPONENTIAL;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;

            case PLUS:
                if(c == '+'){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else if(c == '='){
                    this.state = states.PERFECTMATCH;
                    this.token = Tokens.O_INCREMENT;
                }
                else{
                    this.state = states.ENDMATCH;
                    this.token = Tokens.O_PLUS;
                }
                break;
        }
        return this.state.publicState;
    }


    private String error;
    private Tokens token;
    private states state;
    public void separator(){
        this.reset();
    }
    @Override public String getError() {
        return this.error;
    }
    @Override public Tokens getMatchToken() {
        return this.token;
    }
}

