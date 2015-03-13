
package codemasters;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * @author frasiek Michał Fraś <frasiek@gmail.com>
 */
public class Pakowacz {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Podaj 1 parametr z plikiem do spakowania.");
            System.exit(1);
        }
        try {
            Reader reader = new Reader(args[0]);
            Compressor compressor = new Compressor(reader.getList());
            compressor.compress();
        } catch (FileNotFoundException e){
            System.out.println("Nie znaleziono pliku "+e.getMessage());
        } catch (IOException e){
            System.out.println("Blad I/O "+e.getMessage());
        } catch (Exception e){
            throw e;
//            System.out.println("Nieznany blad "+e.getMessage());
        }
    }
    
}
