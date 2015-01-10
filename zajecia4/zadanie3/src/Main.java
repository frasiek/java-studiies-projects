import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {

	public static void main(String[] args) {
		
		URI uri;
		URL url;
		try {
			uri = new URI("http", "andrzej.augustynowicz.eu","/dydaktyka/jps_z/lab4/zad3/test page.txt", null);
			url = uri.toURL();
			try(
					InputStream istream = url.openStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(istream, "utf8"));	
				){
				char[] chunk = new char[16384];
				int readen = 0;
				while((readen = reader.read(chunk)) != -1){
					System.out.println(chunk);
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
