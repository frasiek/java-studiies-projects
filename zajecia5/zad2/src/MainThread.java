import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class MainThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length<3){
			System.out.println("Wymagane sa trzy paramatry! adres port sciezka");
			System.exit(0);
		}
		
		try(
			Socket http = new Socket(args[0], Integer.parseInt(args[1]));
		){
			http.setSoTimeout(100000);
			BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
			
			out.write("GET "+args[2]+" HTTP/1.1\r\n");
			out.write("Host: "+args[0]+"\r\n\r\n");
			out.flush();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if(inputLine.isEmpty()){
					continue;
				}
				System.out.println(inputLine);
			}
			in.close();
			out.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
