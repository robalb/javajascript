package com.robalb;

public class Token {
    //constants for the different Token Value types that a token can have
    public static final int TV_NUMBER = 1;
    public static final int TV_WORD = 2;
    public static final int TV_FIXED = 3;
    public static final int TV_BOOLEAN = 4;

    private final Tokens tokenType;

    private String wval = "";
    private double nval = 0.0;
    private boolean bval = true;


    public Token(Tokens tokenType){
        if(tokenType.valueType != Token.TV_FIXED) throw new RuntimeException("this token requires a param");
        this.tokenType = tokenType;
    }

    public Token(Tokens tokenType, String word_value){
        if(tokenType.valueType != Token.TV_WORD) throw new RuntimeException("this token requires a different param type");
        this.tokenType = tokenType;
        this.wval = word_value;
    }

    public Token(Tokens tokenType, double number_value){
        if(tokenType.valueType != Token.TV_NUMBER) throw new RuntimeException("this token requires a different param type");
        this.tokenType = tokenType;
        this.nval = number_value;
    }

    public Token(Tokens tokenType, boolean boolean_value){
        if(tokenType.valueType != Token.TV_BOOLEAN) throw new RuntimeException("this token requires a different param type");
        this.tokenType = tokenType;
        this.bval = boolean_value;
    }

    public int valueType(){
        return this.tokenType.valueType;
    }

    public Tokens type(){
        return this.tokenType;
    }

    public String wval(){
        return this.wval;
    }
    public double nval(){
        return this.nval;
    }
    public boolean bval(){
        return this.bval;
    }
}
