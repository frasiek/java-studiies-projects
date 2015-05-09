/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codemasters2;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frasiek
 */
public class Unpacker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Element> elements = new ArrayList<>();
        ArrayList<Integer> dictionary = new ArrayList<>();
        try(BufferedInputStream in =  new BufferedInputStream(Files.newInputStream(Paths.get(args[0])));){
            byte[] header = new byte[4];
            while(in.read(header) != -1){
                Integer item = ByteBuffer.wrap(header).getInt();
                if(item == 0xffffffff){
                    break;
                }
                dictionary.add(item);
            }
            header = new byte[2];
            while(in.read(header) != -1){
                Integer lat = Byte.toUnsignedInt(header[0]);
                Integer lng = Byte.toUnsignedInt(header[1]);
                lat = dictionary.get(lat);
                lng = dictionary.get(lng);
                if(elements.size() == 0){
                    elements.add(new Element(lat, lng));
                } else {
                    elements.add(new Element(elements.get(elements.size()-1), lat, lng));
                }
            }
            
            try(BufferedWriter outWriter= new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get("dane_decompress"),StandardOpenOption.CREATE_NEW),"UTF-8"));){
                for(Element el : elements){
                    outWriter.write(el.toString());
                }
            }
            
        } catch (IOException ex) {
            System.out.println("Nastapil blad I/O"+ex.toString());
        }
    }
    
}
