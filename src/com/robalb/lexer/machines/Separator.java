package com.robalb.lexer.machines;

public enum Separator implements Machine{
    START {
        @Override public String step(char currentChar) {
            if (currentChar == ' ' || currentChar == '\t') return "SPACE";
            return "NO_MATCH";
        }
    },
    SPACE{
        @Override public String step(char currentChar){
            if(currentChar == ' ' || currentChar == '\t') return "SPACE";
            return "END_MATCH";
        }
    }
}
