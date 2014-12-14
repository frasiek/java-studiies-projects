import static java.nio.file.StandardOpenOption.CREATE_NEW;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Proszê przygotowaæ program kopiuj¹cy dowolny plik. Ka¿dy plik
 * powinien byæ czytany fragmentami co 16kb. Proszê przetestowaæ
 * ró¿ne opcje zapisu pliku.
 * @author Micha³ Fraœ
 */
public class Main {

	public static void main(String[] args) {
		try(
			BufferedReader inReader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(args[0])), "UTF-8"));
			BufferedWriter outWriter= new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(args[1]),CREATE_NEW),"UTF-8"));
		){
			
			char[] chunk = new char[16384];
			int readen = 0;
			while((readen = inReader.read(chunk)) != -1){
				outWriter.write(chunk, 0, readen);
			}
		} catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Podaj 2 paramerty: plik_in plik_out");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
