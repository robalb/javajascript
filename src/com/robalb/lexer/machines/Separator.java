package com.robalb.lexer.machines;

import com.robalb.Tokens;

public class Separator implements Machine{
    /**
     * all the internal states, with their associated public state
     */
    private enum states {
        INITIAL(Machine.STEPPING),
        SPACE(Machine.STEPPING),

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
        //this is an extremely simple regex, an if-else implementation will be enough
        switch(this.state){
            case INITIAL:
                if(c == ' ' || c == '\t') this.state = states.SPACE;
                else this.state = states.NOMATCH;
                break;

            case SPACE:
                if(c != ' ' && c != '\t'){
                    this.state = states.ENDMATCH;
                    this.token = Tokens.SEPARATOR;
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
