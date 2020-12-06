package com.robalb.lexer.machines;

import com.robalb.Token;

public class Separator implements Machine{

    @Override
    public void reset() {

    }

    @Override
    public int step(char currentChar) {
        return 0;
    }

    @Override
    public String getError() {
        return null;
    }

    @Override
    public Token getToken() {
        return null;
    }
}
