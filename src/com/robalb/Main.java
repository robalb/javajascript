package com.robalb;

import com.robalb.lexer.Tokenizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
/*
    //https://astexplorer.net/
 */
public class Main{
    public static void main(String[] args){
        //this code is temporary, should be replaced by a proper stream class, and a proper cli wrapper
        String filePath = "test1.txt";

        FileInputStream fileStream;
        try {
            fileStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
            return;
        }
        BufferedReader buffReader = new BufferedReader(
                new InputStreamReader(fileStream, StandardCharsets.UTF_8)
        );

        Tokenizer tokenizer = new Tokenizer(buffReader);

        ArrayList<Token> a = tokenizer.getTokens();
        System.out.println(a.get(0).type());
        System.out.println(a.get(0).valueType());
    }
}
