package codemasters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author frasiek
 */
public class Reader {

    private final ArrayList<Element> lines = new ArrayList<>();

    public Reader(String file) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(new Element(lines.size(), line));
            }
        }
    }

    public ArrayList<Element> getList() {
        return lines;
    }

}
