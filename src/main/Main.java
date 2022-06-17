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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;

import static main.Constants.*;
import static main.StringGenerator.generateStr;
import static main.FilesUtil.readStringsFromFileAndSort;

public class Main {
    public static volatile boolean prodIsDone = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        Files.createDirectory(DIRECTORY);
        Path file = Files.createFile(FILE_TXT);
        StringGenerator.generateStrings(1_000_000, 100, file);

        //chopping file into small sorted peaces
        List<String> filenames = new ArrayList<>();
        long start = System.currentTimeMillis();
        BlockingQueue<List<String>> queue = new LinkedBlockingQueue<>(1);
        try (BufferedReader reader = Files.newBufferedReader(FILE_TXT)) {
            Thread producer = new Thread(() -> {
                try {
                    while (reader.ready()) {
                        List<String> sorted = readStringsFromFileAndSort(reader, BATCH_SIZE);
                        queue.put(sorted);
                    }
                    prodIsDone = true;
                } catch (Exception ignore) {
                }
            });
            producer.start();
            Thread consumer = new Thread(() -> {
                try {
                    while (!prodIsDone || !queue.isEmpty()) {
                        List<String> sorted = queue.take();
                        Path newFile = Paths.get(String.valueOf(DIRECTORY),generateStr());
                        Files.createFile(newFile);
                        try (PrintStream printStream = new PrintStream(Files.newOutputStream(newFile))) {
                            Objects.requireNonNull(sorted).forEach(printStream::println);
                        }
                        filenames.add(newFile.getFileName().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            consumer.start();
            consumer.join();

            long end = System.currentTimeMillis();
            System.out.printf("chopping time %d - s%n", (end - start) / 1000);

            //external sorting
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            String filename = forkJoinPool.invoke(new MyTask(filenames));
            Path result = Paths.get(String.valueOf(DIRECTORY), filename);
            result.toFile().renameTo(Paths.get(String.valueOf(DIRECTORY),"Sorted.txt").toFile());
            long end2 = System.currentTimeMillis();

            System.out.printf("sorting time %d - s%n", (end2 - end) / 1000);
        }
    }
}
