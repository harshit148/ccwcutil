package org.wc;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class App 
{
    public static List<Counter> createCounters(CommandLine cmd) {
        List <Counter> counters = new ArrayList<>();
        if (cmd.hasOption('l')) {
            counters.add(new LineCounter());
        }
        if (cmd.hasOption('c')) {
            counters.add(new ByteCounter());
        }
        if (cmd.hasOption('m')) {
            counters.add(new CharacterCounter());
        }
        if (cmd.hasOption('w')) {
            counters.add(new WordCounter());
        }
        if (counters.isEmpty()) {
            counters.add(new LineCounter());
            counters.add(new ByteCounter());
            counters.add(new CharacterCounter());
            counters.add(new WordCounter());
        }
        return counters;

    }

    private static void countLinesWordsAndBytes(CommandLine cmd, List<Counter> counters) {
        if (cmd.getArgs().length == 0 ) {
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()))) {
                String line;
                while ((line = bufferedReader.readLine()) !=  null) {
                    for (Counter counter: counters) {
                        counter.count(line);
                    }
                }
            }catch(IOException ioex) {
                System.err.println("Error reading from stdin: " + ioex.getMessage());
            }
        }
        else {
            for ( String file: cmd.getArgs()) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while((line = bufferedReader.readLine())!= null) {
                        for (Counter counter: counters) {
                            counter.count(line);
                        }
                    }
                }catch(IOException ioex) {
                    System.err.println("Error reading from file " + file + ": " + ioex.getMessage());
                }
            }
        }
    }
    public static void main( String[] args)
    {
        Options options = new Options();
        Option byteCount = new Option("c", "bytes", false, "Count of bytes");
        byteCount.setRequired(false);
        options.addOption(byteCount);
        Option lineCount = new Option("l", "lines", false, "Line count");
        lineCount.setRequired(false);
        options.addOption(lineCount);
        Option wordCount = new Option("w", "words", false, "Word count");
        wordCount.setRequired(false);
        options.addOption(wordCount);
        Option charCount = new Option("m", "characters", false, "Count of characters");
        charCount.setRequired(false);
        options.addOption(charCount);

        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            List <Counter> counters = createCounters(cmd);
            countLinesWordsAndBytes(cmd, counters);
            for (Counter counter : counters) {
                counter.printCounts();
            }
            if (cmd.getArgs().length > 0) {
                System.out.println(String.join(" ", cmd.getArgs()));
            }
            else {
                System.out.println();
            }
        }catch (ParseException ex) {
            System.err.println(ex.getMessage());
            formatter.printHelp("wc", options);
        }

    }

}
