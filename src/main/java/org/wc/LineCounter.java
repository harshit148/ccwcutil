package org.wc;

public class LineCounter implements Counter{
    private long lineCount = 0;

    @Override
    public void count(String line) {
        lineCount++;
    }

    @Override
    public void printCounts() {
        System.out.println(lineCount + " ");
    }
}
