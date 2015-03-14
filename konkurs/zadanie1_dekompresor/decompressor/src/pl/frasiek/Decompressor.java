/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.frasiek;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 *
 * @author frasiek
 */
public class Decompressor {

    private static final String OUT_FILE = "dane_decompress";
    private static final String KEY_FILE = "key.txt";
    private static ArrayList<Integer> key = new ArrayList<>();
    private Integer elements;

    public Decompressor(String path) throws FileNotFoundException, IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(path))) {
            Integer lat = in.readInt();
            Integer lng = in.readInt();
            long elements = in.readLong();
            elements--;
            this.loadKey(in);

            File fileTemp = new File(Decompressor.OUT_FILE);
            if (fileTemp.exists()) {
                fileTemp.delete();
            }
            Element element = new Element(lat, lng);
            try (BufferedWriter outWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(Decompressor.OUT_FILE), StandardOpenOption.CREATE_NEW), "UTF-8"));) {
                outWriter.write(element.toString());
                for (long i = 0; i < elements; i++) {
                    Integer type = (int) (i % key.size());
                    element = new Element(element, key.get(type));
                    outWriter.write(element.toString());
                }
            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args.length != 1) {
            System.out.println("Podaj 1 parametr z plikiem do spakowania.");
            System.exit(1);
        }
        Decompressor decompressor = new Decompressor(args[0]);
    }

    private void loadKey(DataInputStream in) throws FileNotFoundException, IOException {
        elements = in.readInt();
        byte chunk;
        try{
            while(true){
                chunk = in.readByte();
                Integer[] keyPart = interprateByte(chunk);
                if(keyPart.length > 0){
                    for(Integer i : keyPart){
                        key.add(i);
                    }
                }
            }
        } catch (EOFException e){}
        
    }
    
    private Integer[] interprateByte(byte chunk){
        int count = 4;
        if(elements < 4){
            count = elements;
        }
        elements -= 4;
        Integer[] ret = new Integer[count];
        for(int i = 0; i < count; i++){
            byte[] b = new byte[4];
            b[3] = (byte) (chunk);
            b[3] = (byte) ((b[3] >> 6) & 3);
            chunk = (byte) (chunk << 2);
            ret[i] = ByteBuffer.wrap(b).getInt();
        }
        
        return ret;
    }

}

class Element {

    private Integer lat = null;
    private Integer lng = null;

    public Element(Integer lat, Integer lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Element(Element prev, int type) {
        int FIRST_UP = 0;
        int FIRST_DOWN = 1;
        int SECOND_UP = 2;
        int SECOND_DOWN = 3;
        int STEP = 10;

        this.lat = prev.lat;
        this.lng = prev.lng;
        switch (type) {
            case 0:
                this.lat += STEP;
                break;
            case 1:
                this.lat -= STEP;
                break;
            case 2:
                this.lng += STEP;
                break;
            case 3:
                this.lng -= STEP;
                break;
        }
    }

    public String toString() {
        String lat = this.lat.toString().substring(0, 2) + "." + this.lat.toString().substring(2, this.lat.toString().length());
        String lng = this.lng.toString().substring(0, 2) + "." + this.lng.toString().substring(2, this.lng.toString().length());

        if (this.lat < 0) {
            lat = this.lat.toString().substring(0, 3) + "." + this.lat.toString().substring(3, this.lat.toString().length());
        }
        if (this.lng < 0) {
            lng = this.lng.toString().substring(0, 3) + "." + this.lng.toString().substring(3, this.lng.toString().length());
        }

        return lat + "," + lng + "\r\n";
    }
}
