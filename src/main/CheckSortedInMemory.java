package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static main.Constants.DIRECTORY;

public class CheckSortedInMemory {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get(String.valueOf(DIRECTORY), "Sorted.txt");
        try (BufferedReader br = new BufferedReader(Files.newBufferedReader(path))) {
            ArrayList<String> list = new ArrayList<>();
            while (br.ready()){
                list.add(br.readLine());
            }
            List<String> tmp = new ArrayList<>(list);
            Collections.sort(tmp);
            System.out.println(tmp.equals(list));
        }
    }
}
