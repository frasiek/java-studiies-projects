package codemasters;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 *
 * @author frasiek
 */
public class Compressor {

    private static final Integer SEPARATOR = 0xFFFFFFFF;
    private static final String OUT_FILE = "dane_compress";
    private ArrayList<Element> elements;
    private ArrayList<Integer> dictionary = new ArrayList<>();

    public Compressor(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public void compress() throws IOException {
        this.setUpDictionary();
        try (BufferedOutputStream outStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(Compressor.OUT_FILE), StandardOpenOption.CREATE_NEW))) {
            this.writeDictionary(outStream);
            this.writeData(outStream);
        }
    }

    private void setUpDictionary() {
        dictionary.add(elements.get(0).getLng());
        dictionary.add(elements.get(0).getLat());

        Element previous = elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            Element current = elements.get(i);
            Integer tmp = current.subLat(previous.getLat());
            if (dictionary.contains(tmp) == false) {
                dictionary.add(tmp);
            }
            tmp = current.subLng(previous.getLng());
            if (dictionary.contains(tmp) == false) {
                dictionary.add(tmp);
            }
            previous = current;
        }
    }

    private void writeDictionary(BufferedOutputStream out) throws IOException {
        for (Integer element : dictionary) {
            byte[] bytes = ByteBuffer.allocate(4).putInt(element).array();
            out.write(bytes);
        }
        byte[] bytes = ByteBuffer.allocate(4).putInt(Compressor.SEPARATOR).array();
        out.write(bytes);
    }

    private void writeData(BufferedOutputStream out) throws IOException {
        boolean first = true;
        for (Element el : elements) {
            if (first) {
                Integer toWrite = el.getLat();
                byte[] bytes = ByteBuffer.allocate(4).putInt(this.dictionary.indexOf(toWrite)).array();
                out.write(bytes, 3, 1);
                toWrite = el.getLng();
                bytes = ByteBuffer.allocate(4).putInt(this.dictionary.indexOf(toWrite)).array();
                out.write(bytes, 3, 1);
                first = false;
                continue;
            }
            Integer toWrite = el.getLatDiff();
            byte[] bytes = ByteBuffer.allocate(4).putInt(this.dictionary.indexOf(toWrite)).array();
            out.write(bytes, 3, 1);
            toWrite = el.getLngDiff();
            bytes = ByteBuffer.allocate(4).putInt(this.dictionary.indexOf(toWrite)).array();
            out.write(bytes, 3, 1);
        }
    }

}
