package ru.sorter;

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

import static ru.sorter.util.FilesUtil.readSortedStrings;

public class Main {

    public static void main(String[] args) throws IOException {
        String DIRECTORY = args[0];
        String FILE_NAME_INPUT = args[1];
        String FILE_NAME_OUTPUT = args[2];
        long BATCH_SIZE = Long.parseLong(args[3]);
        long NUM_STRINGS = Long.parseLong(args[4]);
        int STRING_BIT_SIZE = Integer.parseInt(args[5]);

        Path directory = Paths.get(DIRECTORY);
        Path file2Sort = Paths.get(DIRECTORY, FILE_NAME_INPUT);

        //generating file if not present
        if (!Files.exists(file2Sort)) {
            long start = System.currentTimeMillis();
            Files.createDirectories(directory);//exception is not thrown
            Files.createFile(file2Sort);
            StringGenerator.generateStrings(NUM_STRINGS, STRING_BIT_SIZE, file2Sort);
            long end = System.currentTimeMillis();
            System.out.printf("generation time %d - s%n", (end - start) / 1000);
        }

        //chopping file into small sorted peaces
        List<String> filenames = new ArrayList<>();
        long start = System.currentTimeMillis();
        try (BufferedReader reader = Files.newBufferedReader(file2Sort)) {
            while (reader.ready()) {
                List<String> sorted = readSortedStrings(reader, BATCH_SIZE);
                Path newFile = Paths.get(DIRECTORY, StringGenerator.generateStr());
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
        String filename = forkJoinPool.invoke(new MergeFilesTask(DIRECTORY, filenames));
        Path result = Paths.get(DIRECTORY, filename);
        result.toFile().renameTo(Paths.get(DIRECTORY, FILE_NAME_OUTPUT).toFile());
        long end2 = System.currentTimeMillis();

        System.out.printf("sorting time %d - s%n", (end2 - end) / 1000);
    }
}

