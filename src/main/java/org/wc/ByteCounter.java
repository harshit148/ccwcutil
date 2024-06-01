package org.wc;

public class ByteCounter implements Counter{
    private long totalBytes = 0;

    @Override
    public void count(String line) {
        totalBytes += line.getBytes().length;
    }

    @Override
    public void printCounts() {
        System.out.println(totalBytes + " ");
    }
}
