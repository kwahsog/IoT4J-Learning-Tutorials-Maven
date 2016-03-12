/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.ljc;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import java.util.concurrent.Callable;

/**
 * Render a sequence of Morse Code as output on GPIO
 * 
 * @author Carl Jokl
 */
public class MorseCodeDisplay {
    
    public static final long UNIT = 250L;
    
    public static final long PHRASE_GAP_MILLIS = 7 * UNIT;
    public static final long CHARACTER_GAP_MILLIS = 3 * UNIT;
    public static final long ITEM_GAP_MILLIS = UNIT;
    public static final long DOT_LENGTH_MILLIS = UNIT;
    public static final long DASH_LENGTH_MILLIS = 3 * UNIT;
    
    private GpioPinDigitalOutput output;
    
    public MorseCodeDisplay(final GpioPinDigitalOutput output) {
        if (output == null) throw new IllegalArgumentException("Output pin was not set!");
        this.output = output;
    }
    
    public void renderMorse(final boolean[][][] morse) {
        if (morse != null && morse.length > 0) {
            Callable<Void> current = null;
            for (int phraseIndex = morse.length - 1; phraseIndex >= 0; phraseIndex--) {
                boolean[][] currentPhrase = morse[phraseIndex];
                for (int characterIndex = currentPhrase.length - 1; characterIndex >= 0; characterIndex--) {
                    boolean[] currentCharacterItems = currentPhrase[characterIndex];
                    current = new CharacterIterator(output, currentCharacterItems, current);
                }
                if (phraseIndex > 0) {
                    current = new PhraseBoundary(output, current);
                }
            }
            if (current != null) {
                try {
                    current.call();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static class PhraseBoundary implements Callable<Void> {
        
        private final GpioPinDigitalOutput output;
        private final Callable<Void> next;
        
        /**
         * Create a new phrase boundary.
         * 
         * @param output 
         */
        public PhraseBoundary(final GpioPinDigitalOutput output, final Callable<Void> next) {
            if (output == null) throw new IllegalArgumentException("Output cannot be null!");
            this.output = output;
            this.next = next;
        }

        //@Override
        public Void call() throws Exception {
            output.pulse(PHRASE_GAP_MILLIS, PinState.LOW, next);
            return null;
        }
    }
    
    private static class CharacterIterator implements Callable<Void> {
    
        private final GpioPinDigitalOutput output;
        private final boolean[] characterBits;
        private final Callable<Void> next;
        private int currentIndex = 0;
        private boolean gap = false;
        
        public CharacterIterator(final GpioPinDigitalOutput output,                     
                                 final boolean[] characterBits,
                                 final Callable<Void> next) {
            if (output == null) throw new IllegalArgumentException("Output cannot be null!");
            this.output = output;
            this.characterBits = characterBits != null ? characterBits : new boolean[0];
            this.next = next;
        }
        
        public CharacterIterator(final GpioPinDigitalOutput output,
                                 final boolean[] characterBits) {
            this(output, characterBits, null);
        }

        //@Override
        public Void call() {
            System.out.printf("Call at item index: %d gap state: %b \n", currentIndex, gap);
            if (currentIndex < characterBits.length) {
                if (gap) {
                    gap = false;
                    output.pulse(ITEM_GAP_MILLIS, PinState.LOW, this);
                }
                else {
                    gap = true;
                    final boolean dash = characterBits[currentIndex];
                    currentIndex++;
                    output.pulse(dash ? DASH_LENGTH_MILLIS : DOT_LENGTH_MILLIS, PinState.HIGH, this);
                }
            }
            else if (next != null) {
                output.pulse(CHARACTER_GAP_MILLIS, PinState.LOW, next);
            }
            else {
                output.setState(PinState.LOW);
            }
            return null;
        }
         
    }
}
