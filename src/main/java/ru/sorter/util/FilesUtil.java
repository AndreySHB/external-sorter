package ru.sorter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**Performs operations with files and streams*/
public class FilesUtil {
    /**Merges 2 sorted files with given names, located in a given directory into 1 sorted file.
     * Returns the name of a new sorted file  */
    public static String mergeFiles(String directory, String leftName, String rightName) throws IOException {
        String fileName = leftName + rightName;
        Path newFile = Paths.get(directory, fileName);
        Files.deleteIfExists(newFile);
        Files.createFile(newFile);
        Path leftFile = Paths.get(directory, leftName);
        Path rightFile = Paths.get(directory, rightName);

        try (BufferedReader readerLeft = new BufferedReader(Files.newBufferedReader(leftFile));
             BufferedReader readerRight = new BufferedReader(Files.newBufferedReader(rightFile));
             PrintStream psNewFile = new PrintStream(Files.newOutputStream(newFile))) {

            String left = readerLeft.readLine();
            String right = readerRight.readLine();
            String min = left.compareTo(right) > 0 ? right : left;
            String max = min == left ? right : left;
            psNewFile.println(min);
            boolean readLeft = max == right;
            String s;
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
        return fileName;
    }
    /**Reads n=strNumber strings from source "reader" and returns sorted list */
    public static List<String> readSortedStrings(BufferedReader reader, long strNumber) throws IOException {
        List<String> list = new ArrayList<>();
        for (int i = 0; reader.ready() && i < strNumber; i++) {
            list.add(reader.readLine());
        }
        list.sort(Comparator.naturalOrder());
        return list;
    }
    /**Removes files with given names, located in a given directory*/
    public static void deleteFilesByName(Path directory, String... names) throws IOException {
        for (String s : names) {
            Path p = Paths.get(directory.toString(), s);
            Files.delete(p);
        }
    }
}
