import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Converter {

	public static void main(String[] args) {
		Charset inCharset = Charset.forName("ISO8859-2");
		Charset outCharset = Charset.forName("UTF-8");
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(args[0]), inCharset);
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(args[1]), outCharset);){
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				writer.write(line, 0, line.length());
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
