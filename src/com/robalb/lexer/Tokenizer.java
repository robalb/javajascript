package com.robalb.lexer;

import com.robalb.Token;
import com.robalb.Tokens;
import com.robalb.lexer.machines.*;

import com.robalb.fileStream.ExtendedBuffReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This tokenizer has been built by hand and not using automated tools as an exercise.
 * I'm sure this class could have been done in 100 beautiful lines of code using some method i don't know.
 * Instead, it uses it's own hardcoded (and unnecessarily backtracking) ugly-ass regex engine, spit into different modules. Enjoy
 */
public class Tokenizer {

    protected ExtendedBuffReader buffStream;

    public Tokenizer(ExtendedBuffReader buffStream){
        this.buffStream = buffStream;
    }

    public ArrayList<Token> getTokens() throws IOException {

        ArrayList<Token> tokenizedInput = new ArrayList<>();

        //TODO: implement and move cursor position logic to dedicated class
//        final boolean isNewline = !isSpace && ( intC == 0x00D ||
//                intC == 0x000A || intC == 0x2028 || intC == 0x2029
//        );
//        if(ignoreLf){
//            if(intC != 0x000D) ignoreLf = false;
//            if(isNewline && intC != 0x000A){
//                lineTerminatorsContained = true;
//            }
//        }else{
//            if(intC == 0x000D){
//                ignoreLf = true;
//            }
//            if(isNewline){
//                lineTerminatorsContained = true;
//            }
//        }
        //position variables, updated when a newline cursor is met
        long position = 0;
        long markedPosition = 0;
        int line = 1;

        //declare array of statemachines that will receives the stream character one by one.
        //When a machine doesn't find any match the code will backtrack and move to the next one, so the order
        //in this array is very important
        //note: bactracking has been added to keep different tokens separated in different classes and in order. It is
        // possible to integrate all conflicting states in a single machine
        Machine[] machines = {
                new separatorsAndIgnored(),
                new Identifiers(),
                new NumericAndBigintLiterals(),
                //regex /[anything]/
                new Punctuators(),
                new StringAndTemplateLiterals(),
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

            //the machine found a result
            else if(state == Machine.PERFECTMATCH || state == Machine.ENDMATCH){
                //handle token result
                Token t = machines[machineIndex].getToken();
                //update line position
                //TODO: remove, replace with a better position system
                if(t.type() == Tokens.LINE_TERMINATOR){
                  line++;
                  position = 0;
                }
                //add tokens to the stream
                if(t.valueType() != Token.TV_IGNORE){
                    //replace multiline comments with a line terminator (see comment token docs)
                    if(t.type() == Tokens.MUTLILINE_COMMENT){
                        t = new Token(Tokens.LINE_TERMINATOR);
                    }
                    tokenizedInput.add(t);
                }

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
