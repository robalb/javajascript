package com.robalb.lexer.machines;

public enum BasicMathOperands implements Machine{
    START {
        @Override public String step(char currentChar) {
            if (currentChar == '+') return "PLUS";
            else if(currentChar == '-') return "MINUS";
            else if(currentChar == '*') return "MULT";
            else return "NO_MATCH";
        }
    },
    PLUS{
        @Override public String step(char currentChar){
            if(currentChar == ' ' || currentChar == '\t') return "SPACE";
            return "END_MATCH";
        }
    }
}
