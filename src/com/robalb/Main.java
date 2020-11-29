package com.robalb;

import com.robalb.lexer.Tokenizer;

import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        String program = "12 + (1 - 3) /* a comment */";

        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> a = tokenizer.tokenize(program);
        System.out.println(a.get(0).type());
    }
}
