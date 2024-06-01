package org.wc;

public class CharacterCounter implements Counter{
    private long totalCharacters = 0;

    @Override
    public void count(String line) {
        totalCharacters += line.length();
    }

    @Override
    public void printCounts() {
        System.out.println(totalCharacters + " ");
    }
}
