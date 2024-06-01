package org.wc;

public class WordCounter implements Counter{
    private long totalWords = 0;

    @Override
    public void count(String line) {
        totalWords += line.split("\\s+").length;
    }

    @Override
    public void printCounts() {
        System.out.println(totalWords + " ");
    }
}
