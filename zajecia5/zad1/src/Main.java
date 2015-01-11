import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		try(
				Socket client = new Socket("ntp.task.gda.pl", 13);
		){
			client.setSoTimeout(500);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if(inputLine.isEmpty()){
					continue;
				}
				System.out.println(inputLine);
			}
			in.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
