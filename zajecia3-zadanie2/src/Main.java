import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Main {

	public static void main(String[] args) {
		File fIn;
		FileReader in = null;
		
		File fOut;
		FileWriter out = null;
		try{
			fIn = new File(args[0]);
			fOut = new File(args[1]);
			
			in = new FileReader(fIn);
			out = new FileWriter(fOut);
			
			int c;
			while((c = in.read()) != -1){
				out.write(c);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (Exception e){
			System.out.println(e.getMessage());
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {}
			}
			if(out != null){
				try{
					out.close();
				} catch (IOException e) {}
			}
		}
		
	}

}
