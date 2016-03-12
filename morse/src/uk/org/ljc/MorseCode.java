/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.ljc;

/**
 * Convert text to Morse Code
 * 
 * @author Carl Jokl
 */
public class MorseCode {
    
    public enum MorseChar {
        A('a', false, true),
        B('b', true, false, false, false),
        C('c', true, false, true, false),
        D('d', true, false, false),
        E('e', false),
        F('f', false, false, true, false),
        G('g', true, true, false),
        H('h', false, false, false, false),
        I('i', false, false),
        J('j', false, true, true, true),
        K('k', true, false, true),
        L('l', false, true, false, false),
        M('m', true, true),
        N('n', true, false),
        O('o', true, true, true),
        P('p', false, true, true, false),
        Q('q', true, true, false, true),
        R('r', false, true, false),
        S('s', false, false, false),
        T('t', true),
        U('u', false, false, true),
        V('v', false, false, false, true),
        W('w', false, true, true),
        X('x', true, false, false, true),
        Y('y', true, false, true, true),
        Z('z', true, true, false, false),
        ONE('1', false, true, true, true, true),
        TWO('2', false, false, true, true, true),
        THREE('3', false, false, false, true, true),
        FOUR('4', false, false, false, false, true),
        FIVE('5', false, false, false, false, false),
        SIX('6', true, false, false, false, false),
        SEVEN('7', true, true, false, false, false),
        EIGHT('8', true, true, true, false, false),
        NINE('9', true, true, true, true, false),
        ZERO('0', true, true, true, true, true);
            
        private final char character;
        private final boolean[] code;
        
        private MorseChar(final char character, final boolean... code) {
            this.character = character;
            this.code = code;
        }
        
        public char getChar() {
            return character;
        }
        
        public boolean[] getCode() {
            return code;
        }
    }
    
    private String getMorseCode(String input){
        char[] a = new char[100];
        StringBuilder output = new StringBuilder();
        int out_cnt = 0;
        
          final String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h", "i",
        "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
        "v", "w", "x", "y", "z", " "};
 
            final String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
          ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-",  ".-.", "...", "-", "..-",
          "...-" ,".--" ,"-..-", "-.--", "--..", "|"};
        return "";    
    }
            
    public static boolean[] wordToMorseCode(String word) {
       return new boolean[] {}; 
    }
}
