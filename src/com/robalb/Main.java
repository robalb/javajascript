package com.robalb;

import com.robalb.fileStream.ExtendedBuffReader;
import com.robalb.lexer.Tokenizer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//
//
//             how do you eat an elephant?
//
//


/*
    //https://astexplorer.net/
 */
public class Main{
    public static void main(String[] args){
        //this code is temporary, should be replaced by a proper stream class, and a proper cli wrapper
        String filePath = "C:\\Users\\Alberto\\IdeaProjects\\parserexp\\src\\com\\robalb\\test1.js";

        FileInputStream fileStream;
        try {
            fileStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
            return;
        }
        ExtendedBuffReader buffReader = new ExtendedBuffReader(
                new InputStreamReader(fileStream, StandardCharsets.UTF_8)
        );

        Tokenizer tokenizer = new Tokenizer(buffReader);

        ArrayList<Token> a;
        try {
            a = tokenizer.getTokens();
        } catch (IOException e) {
            System.out.println("IOexception occurred while reading the file");
            e.printStackTrace();
            return;
        }
        System.out.println("[debug] printing the stream tokens");
        for(Token t: a){
            System.out.print(t.type());
            System.out.print(" ");
            switch (t.valueType()) {
                case Token.TV_BOOLEAN -> System.out.println(t.bval());
                case Token.TV_NUMBER -> System.out.println(t.nval());
                case Token.TV_WORD -> System.out.println(t.wval());
                default -> System.out.println(" ");
            }
        }
    }
}
