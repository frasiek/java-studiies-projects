/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waski;

import com.sun.xml.internal.ws.util.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

/**
 *
 * @author frasiek
 */
public class Waski {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.net.MalformedURLException
     * @throws java.io.UnsupportedEncodingException
     * @throws java.net.URISyntaxException
     */
    public static void main(String[] args) throws IOException, MalformedURLException, UnsupportedEncodingException, URISyntaxException {
        if (args.length < 1) {
            System.out.println("Podaj url w parametrze");
            System.exit(0);
        }
        Waski main = new Waski();
        Integer ile_slow = main.policz(args[0]);
        System.out.println("Ta strona zawiera "+ile_slow.toString()+" slow.");
    }

    //na bazie prezentacji http://andrzej.augustynowicz.eu/dydaktyka/jps_z/lab4/prezentacja.pdf 
    protected URL getURL(String urlStr) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException {
        URL url = new URL(URLDecoder.decode(urlStr, "utf8"));
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        return uri.toURL();
    }

    protected int policz(String raw_url) throws IOException, MalformedURLException, UnsupportedEncodingException, URISyntaxException {
        URL url = getURL(raw_url);
        InputStream istream = url.openStream();
        int ilosc_spacji = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
            String page = "";
            String chunk = "";
            while ((chunk = reader.readLine()) != null) {
                page += chunk; //czytanie calej striony do pliku
            }
            String tekst = page.replaceAll("\\<.*?>","");
            ilosc_spacji = tekst.length() - tekst.replace(" ", "").length();
        } finally {
            istream.close();
        }
        return ilosc_spacji + 1;
    }
}
