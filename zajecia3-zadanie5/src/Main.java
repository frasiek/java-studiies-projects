import static java.nio.file.StandardOpenOption.CREATE_NEW;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Prosz� przygotowa� program kopiuj�cy dowolny plik. Ka�dy plik
 * powinien by� czytany fragmentami co 16kb. Prosz� przetestowa�
 * r�ne opcje zapisu pliku.
 * @author Micha� Fra�
 */
public class Main {

	public static void main(String[] args) {
		try(BufferedInputStream inStream =  new BufferedInputStream(Files.newInputStream(Paths.get(args[0])));
			BufferedOutputStream outStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(args[1]), CREATE_NEW)))	{
			
			byte[] chunk = new byte[16384];
			int readen = 0;
			while((readen = inStream.read(chunk)) != -1){
				outStream.write(chunk, 0, readen);
			}
		} catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Podaj 2 paramerty: plik_in plik_out");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
