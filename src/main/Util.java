package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static main.Constants.DIRECTORY;

public class Util {
    public static String merge2FilesAndDeletePrevious(String leftName, String rightName) throws IOException {
        String fileName = leftName + rightName;
        Path newFile = Paths.get(String.valueOf(DIRECTORY), fileName);
        Files.createFile(newFile);
        Path leftFile = Paths.get(String.valueOf(DIRECTORY), leftName);
        Path rightFile = Paths.get(String.valueOf(DIRECTORY), rightName);

        try (BufferedReader readerLeft = new BufferedReader(Files.newBufferedReader(leftFile));
             BufferedReader readerRight = new BufferedReader(Files.newBufferedReader(rightFile));
             PrintStream psNewFile = new PrintStream(Files.newOutputStream(newFile))) {

            String left = readerLeft.readLine();
            String right = readerRight.readLine();
            String min = left.compareTo(right) > 0 ? right : left;
            String max = min == left ? right : left;
            psNewFile.println(min);
            boolean readLeft = max == right;
            String s = null;
            BufferedReader trueReader = readLeft ? readerLeft : readerRight;
            while (trueReader.ready()) {
                s = trueReader.readLine();
                if (s.compareTo(max) < 0) {
                    psNewFile.println(s);
                } else {
                    psNewFile.println(max);
                    max = s;
                    trueReader = trueReader == readerLeft ? readerRight : readerLeft;//changing reader
                }
            }
            psNewFile.println(max);
            trueReader = readerLeft.ready() ? readerLeft : readerRight;
            while (trueReader.ready()) {
                psNewFile.println(trueReader.readLine());
            }
        }
        Files.delete(leftFile);
        Files.delete(rightFile);
        return fileName;
    }

    public static List<String> readAndSortNStrings(BufferedReader reader, int batchSize) throws IOException {
        List<String> list = new ArrayList<>();
        for (int i = 0; reader.ready() && i < batchSize; i++) {
            list.add(reader.readLine());
        }
        list.sort(Comparator.naturalOrder());
        return list;
    }
}
