import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;

public class Main2 {

	public static void main(String[] args) {
		
		URI uri;
		URL url;
		try {
			uri = new URI("http", "andrzej.augustynowicz.eu","/dydaktyka/jps_z/lab4/zad3/test page.txt", null);
			url = uri.toURL();
			try{
		        //Delete if tempFile exists
		        File fileTemp = new File("out.html");
		          if (fileTemp.exists()){
		             fileTemp.delete();
		          }   
		      }catch(Exception e){
		         // if any error occurs
		         e.printStackTrace();
		      }

			try(
					InputStream istream = url.openStream();
					BufferedInputStream reader = new BufferedInputStream(istream);
					OutputStream ostream = Files.newOutputStream(Paths.get("out.html"), CREATE_NEW, TRUNCATE_EXISTING);
					BufferedOutputStream bout = new BufferedOutputStream(ostream);
				){
				byte[] chunk = new byte[16384];
				int readen = 0;
				while((readen = reader.read(chunk)) != -1){
					bout.write(chunk, 0, readen);
				}
				
			} catch (Exception ex){
				ex.printStackTrace();
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
