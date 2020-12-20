package com.robalb;

public class Token {
    //constants for the different Token Value types that a token can have

    /**
     * describes the type of value associated to a token:
     * a numeric value, specifically a double
     */
    public static final int TV_NUMBER = 1;

    /**
     * describes the type of value associated to a token:
     * a String
     */
    public static final int TV_WORD = 2;

    /**
     * describes the type of value associated to a token:
     * none, the token name itself is enough to represent it.
     */
    public static final int TV_FIXED = 3;

    /**
     * describes the type of value associated to a token:
     * a boolean value
     */
    public static final int TV_BOOLEAN = 4;

    /**
     * describes the type of value associated to a token:
     * ignore token - the token with this value should be ignored and not added to the tokenized output array/stream
     */
    public static final int TV_IGNORE = 5;

    private final Tokens tokenType;

    private String wval = "";
    private double nval = 0.0;
    private boolean bval = true;


    public Token(Tokens tokenType){
        if(tokenType.valueType != Token.TV_FIXED && tokenType.valueType != Token.TV_IGNORE) throw new RuntimeException("this token requires a param");
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
