import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class MainThread {

	public static void main(String[] args) {
		Console console = System.console();
		String to = console.readLine("Do:");
		String subject = console.readLine("Temat:");
		String message = console.readLine("Tresc:");
		String line = null;
		try(
				Socket smtp = new Socket("ux.up.krakow.pl", 25);
		){
			smtp.setSoTimeout(2000);
			BufferedReader in = new BufferedReader(new InputStreamReader(smtp.getInputStream(), "utf-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(smtp.getOutputStream()));
			
			line = in.readLine();
			if(!line.startsWith("220")){
				System.out.println("Server error: "+line);
				return;
			}
//			System.out.println("Connected");
			
			out.write("HELO srv\r\n");
			out.flush();
			
			line = in.readLine();
			if(!line.startsWith("250")){
				System.out.println("Server error: "+line);
				return;
			}
			
//			System.out.println("Helloed");
			
			out.write("MAIL FROM:<frasiek@up.krakow.pl>\r\n");
			out.flush();
			
			
			line = in.readLine();
			if(!line.startsWith("250")){
				System.out.println("Server error: "+line);
				return;
			}
			
//			System.out.println("From SET");
			
			out.write("RCPT TO:<"+to+">\r\n");
			out.flush();
			
			line = in.readLine();
			if(!line.startsWith("250")){
				System.out.println("Server error: "+line);
				return;
			}
			
//			System.out.println("To SET");
			
			out.write("DATA\r\n");
			out.flush();
			
			line = in.readLine();
			if(!line.startsWith("354")){
				System.out.println("Server error: "+line);
				return;
			}
			
			System.out.println("Data started");
			
			out.write("Subject: "+subject+"\r\n");
			out.write(message+"\r\n");
			out.write(".\r\n");
			out.flush();
			
			line = in.readLine();
			if(!line.startsWith("250")){
				System.out.println("Server error: "+line);
				return;
			}
			
			System.out.println("Message sent: "+line);
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}