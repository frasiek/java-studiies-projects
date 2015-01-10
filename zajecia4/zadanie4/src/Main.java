import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Main {

	public static void main(String[] args) {

		URI uri;
		URL url;
		URLConnection cnn = null;
		try {
			uri = new URI("http", "postcatcher.in","/catchers/54b136922310af0200001323", null);
			url = uri.toURL();
			cnn = url.openConnection();
			cnn.setDoInput(true);
			cnn.setDoOutput(true);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try(
				OutputStream dataStream = cnn.getOutputStream();
				BufferedWriter data = new BufferedWriter(new OutputStreamWriter(dataStream));
				){
			data.write(URLEncoder.encode("test=123","utf8"));

		} catch (Exception ex){
			ex.printStackTrace();
		}

	
		try(
				InputStream response = cnn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(response, "utf8"));	
		){
			char[] chunk = new char[16384];
			int readen = 0;
			while((readen = reader.read(chunk)) != -1){
				System.out.print(chunk);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
