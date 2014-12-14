import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {

	public static void main(String[] args) {
		try{
			Path in = Paths.get(args[0]);
			byte[] buf;
			buf = Files.readAllBytes(in);

			Path out = Paths.get(args[1]);
			Files.write(out, buf);
		} catch (IOException e){
			System.out.println("Wystapil blad: "+e.getMessage());
		} catch( ArrayIndexOutOfBoundsException e){
			System.out.println("Podaj 2 paremetry: plik_in plik_out");
		}

		
	}

}
