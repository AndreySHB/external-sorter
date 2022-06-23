import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.sorter.Main;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static ru.sorter.StringGenerator.generateStr;

@ContextConfiguration
public class SortedTest {

    @Test
    void isSorted() throws Exception {
        Path tempDirectory = Files.createTempDirectory(generateStr());
        String inputFileName = generateStr();
        String outputFileName = "Sorted";
        String[] args = {tempDirectory.toString(), inputFileName, outputFileName, "10000", "100000", "100"};

        try {
            Main.main(args);
            try (BufferedReader br = new BufferedReader(Files
                    .newBufferedReader(Paths.get(tempDirectory.toString(), outputFileName)))) {
                ArrayList<String> list = new ArrayList<>();
                while (br.ready()) {
                    list.add(br.readLine());
                }
                List<String> tmp = new ArrayList<>(list);
                Collections.sort(tmp);
                Assertions.assertEquals(tmp, list);
            }
        } finally {
            File file = tempDirectory.toFile();
            Arrays.stream(Objects.requireNonNull(file.listFiles())).sequential().forEach(File::delete);
            file.delete();
        }
    }
}
