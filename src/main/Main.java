package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;

import static main.Constants.*;
import static main.FilesUtil.readStringsFromFileAndSort;
import static main.StringGenerator.generateStr;

public class Main {

    public static void main(String[] args) throws IOException {
        Files.createDirectory(DIRECTORY);
        Path file = Files.createFile(FILE_TXT);
        StringGenerator.generateStrings(NUM_STRINGS, 100, file);

        //chopping file into small sorted peaces
        List<String> filenames = new ArrayList<>();
        long start = System.currentTimeMillis();
        try (BufferedReader reader = Files.newBufferedReader(FILE_TXT)) {
            while (reader.ready()) {
                List<String> sorted = readStringsFromFileAndSort(reader, BATCH_SIZE);
                Path newFile = Paths.get(String.valueOf(DIRECTORY), generateStr());
                Files.createFile(newFile);
                try (PrintStream printStream = new PrintStream(Files.newOutputStream(newFile))) {
                    Objects.requireNonNull(sorted).forEach(printStream::println);
                }
                filenames.add(newFile.getFileName().toString());
            }
        }
        long end = System.currentTimeMillis();
        System.out.printf("chopping time %d - s%n", (end - start) / 1000);

        //external sorting
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        String filename = forkJoinPool.invoke(new MyTask(filenames));
        Path result = Paths.get(String.valueOf(DIRECTORY), filename);
        result.toFile().renameTo(Paths.get(String.valueOf(DIRECTORY), "Sorted.txt").toFile());
        long end2 = System.currentTimeMillis();

        System.out.printf("sorting time %d - s%n", (end2 - end) / 1000);
    }
}
