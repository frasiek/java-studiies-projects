/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.frasiek;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author frasiek
 */
public class Compressor {

    private static final String OUT_FILE = "dane_compress";
    private final String inPath;
    private ArrayList<Element> elements = new ArrayList<>();

    public Compressor(String path) throws FileNotFoundException, IOException, Exception {
        inPath = path;
        try (BufferedReader br = new BufferedReader(new FileReader(inPath))) {
            String line;
            long numberOfRows = 0;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    elements.add(new Element(line));
                    numberOfRows++;
                }
            }
            Element el = elements.get(0);

            Analizer analizer = new Analizer(elements);
            Dictionary dict = new Dictionary(analizer.analize());
            File fileTemp = new File(Compressor.OUT_FILE);
            if (fileTemp.exists()) {
                fileTemp.delete();
            }
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(Compressor.OUT_FILE))) {
                out.writeInt(el.getLat());
                out.writeInt(el.getLng());
                out.writeLong(numberOfRows);
                dict.writeDict(out);
            }
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        if (args.length != 1) {
            System.out.println("Podaj 1 parametr z plikiem do spakowania.");
            System.exit(1);
        }
        Compressor compressor = new Compressor(args[0]);
    }

}

class Dictionary {

    private ArrayList<Byte> key = null;

    public Dictionary(ArrayList<Byte> key) {
        this.key = key;
    }

    public void writeDict(DataOutputStream out) throws IOException {
        Integer keySize = key.size();
        out.writeInt(keySize);
        while (key.size() % 4 != 0) {
            key.add(key.get(0));
        }
        for (int i = 0; i < keySize; i += 4) {
            out.write(sum(key.get(i), key.get(i + 1), key.get(i + 2), key.get(i + 3)));
        }
    }

    private byte[] sum(Byte b1, Byte b2, Byte b3, Byte b4) {
        byte[] out = new byte[1];
        out[0] = b1;
        out[0] = (byte) (out[0] << 2);
        out[0] = (byte) (out[0] | b2);
        out[0] = (byte) (out[0] << 2);
        out[0] = (byte) (out[0] | b3);
        out[0] = (byte) (out[0] << 2);
        out[0] = (byte) (out[0] | b4);
        return out;
    }

}

class Element {

    private final Integer lat;
    private final Integer lng;

    private int type;

    private Integer latDiff;
    private Integer lngDiff;

    private static int FIRST_UP = 0;
    private static int FIRST_DOWN = 1;
    private static int SECOND_UP = 2;
    private static int SECOND_DOWN = 3;

    public Element(String line) {
        String[] tmp = line.split(",");
        lat = Integer.parseInt(tmp[0].replace(".", ""));
        lng = Integer.parseInt(tmp[1].replace(".", ""));
    }

    public Integer subLat(Integer lat) {
        return this.latDiff = this.lat - lat;
    }

    public Integer subLng(Integer lng) {
        return this.lngDiff = this.lng - lng;
    }

    public Integer getLat() {
        return lat;
    }

    public Integer getLng() {
        return lng;
    }

    public int countDifference(Element prev) {
        this.subLat(prev.getLat());
        this.subLng(prev.getLng());

        if (latDiff > 0) {
            type = FIRST_UP;
        }
        if (latDiff < 0) {
            type = FIRST_DOWN;
        }
        if (lngDiff > 0) {
            type = SECOND_UP;
        }
        if (lngDiff < 0) {
            type = SECOND_DOWN;
        }
        return type;
    }
}

class Analizer {

    private ArrayList<Element> list = new ArrayList<>();
    private ArrayList<Integer> types = new ArrayList<>();

    public Analizer(ArrayList<Element> list) {
        this.list = list;
    }

    public ArrayList<Byte> analize() throws Exception {
        this.countElements();
        return this.findLargestSet();
    }

    private void countElements() {
        boolean first = true;
        Element prev = null;
        for (Element el : list) {
            if (first) {
                prev = el;
                first = false;
                continue;
            }

            this.types.add(el.countDifference(prev));

            prev = el;
        }
    }

    private ArrayList<Byte> findLargestSet() throws Exception {
        ArrayList<Byte> key = new ArrayList<>();
        byte[] bytes;
        for (Integer s : types) {
            key.add((byte) (s & 3));
        }
        return key;
    }
}
