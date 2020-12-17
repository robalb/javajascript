package com.robalb.lexer;

import com.robalb.Token;
import com.robalb.Tokens;
import com.robalb.lexer.machines.IgnoreType;
import com.robalb.lexer.machines.Machine;
import com.robalb.lexer.machines.SimplePunctuators;

import com.robalb.fileStream.ExtendedBuffReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * I'm sure this class could have been done in 100 beautiful lines of code using some method i don't know
 * instead, it uses it's own hardcoded ugly-ass regex engine, spit into different modules. Enjoy
 */
public class Tokenizer {

    protected ExtendedBuffReader buffStream;

    public Tokenizer(ExtendedBuffReader buffStream){
        this.buffStream = buffStream;
    }

    public ArrayList<Token> getTokens() throws IOException {

        ArrayList<Token> tokenizedInput = new ArrayList<>();

        //TODO: move logic to dedicated class
        //position variables, updated when a newline cursor is met
        long position = 0;
        long markedPosition = 0;
        int line = 1;

        //declare array of statemachines that will receives the stream character one by one.
        //when a machine doesn't find any match, the code will backtrack and move to the next one so the order
        //in this array is very important
        Machine[] machines = {
                new IgnoreType(),
                new SimplePunctuators()
        };
        //the machine that we are stepping through at the moment
        int machineIndex = 0;


        int currentC = -2;

        int MARKBUFFER = 100;
        buffStream.mark(MARKBUFFER);

        //iterate every character int the buffStream
        while(currentC != -1){
            position++;
            currentC = this.buffStream.read();

            //step through the current machine
            int state = machines[machineIndex].step(currentC);

            //the machine encountered a parse error
            if(state == Machine.ERROR){
                System.out.println("parse error encountered");
                System.out.print(machines[machineIndex].getError());
                System.out.printf(" at %d:%d %n", line, position);
                break;
            }

            //the machine require further steps to get a result
            //else if(state == Machine.STEPPING){
            //}

            //the machine found a result
            else if(state == Machine.PERFECTMATCH || state == Machine.ENDMATCH){
                //handle token result
                Token t = machines[machineIndex].getToken();
                if(t.type() == Tokens.LINE_TERMINATOR){
                  line++;
                  position = 0;
                }
//                if(t.valueType() != Token.TV_IGNORE){
                    tokenizedInput.add(t);
//                }

                //handle buffer marks
                if(state == Machine.ENDMATCH){
                    buffStream.markCurrent(MARKBUFFER);
                    markedPosition = position;
                }else{
                    buffStream.mark(MARKBUFFER);
                    markedPosition = position;//off by one? irrelevant, the whole position tracker is buggy and should be separated from this logic
                }
                //resets
                machines[machineIndex].reset();
                buffStream.reset();
                machineIndex = 0;
            }

            //the current machine didn't get a match: backtrack and move to the next machine
            else if(state == Machine.NOMATCH){
                machines[machineIndex].reset();
                buffStream.reset();
                machineIndex++;
                position = markedPosition;
                if(machineIndex == machines.length){
                    System.out.println("parse error encountered");
                    System.out.printf("Unexpected character '%c' at %d:%d %n", (char) currentC, line, position);
                    break;
                }
            }


        }

        return tokenizedInput;
    }
}
