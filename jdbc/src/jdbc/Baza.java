package jdbc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Baza extends Thread {

    /* dane dostepowe do bazy danych */
    private String host;
    private Integer port;
    private String user;
    private String pass;
    private String dbname;
    private Connection connection = null;
    private BufferedReader r;
    private BufferedWriter w;
    private Socket s;

    public Baza(String plikKonfiguracyjny, Socket s) throws IOException {
        this.s = s;
        this.konfiguruj(plikKonfiguracyjny); //wczytuje konfiguracje z pliku
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;

        try {
            connection = DriverManager.getConnection(url, user, pass);
            r = new BufferedReader(new InputStreamReader(this.s.getInputStream(), "utf-8"));
            w = new BufferedWriter(new OutputStreamWriter(this.s.getOutputStream()));
        } catch (SQLException ex) {
            try {
                w.write("Nie udalo sie podlaczyc do bazy danych\r\n");
                w.flush();
            } catch (IOException ex1) {
                Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            //petla czekajaca na dane wpisane przez uzytownika
            String input = null;
            while (true) {
                w.write("Podaj zapytanie: \r\n");
                w.flush();
                input = r.readLine();
                if (input.toLowerCase().trim().equals("q")) {
                    break;//do puki uzytkownik nie wpisze q
                }
                if (input.toLowerCase().startsWith("select") || input.toLowerCase().startsWith("show")) { //wykonujemy zapytanie select
                    Boolean set = false;
                    ResultSet wynik = null;
                    if (input.contains("?")) {
                        wynik = this.noweZapytanieZlozone(input); //uruchamiamy metode zapytania zlozonego
                    } else {
                        wynik = this.noweZapytanieProste(input); //wykonujemy zapytanie do bazy
                    }
                    this.zrobCosZwynikiem(wynik);
                } else {
                    this.wykonajZapytanie(input);
                }
            }
        } catch (SQLException ex) {
            try {
                w.write("Podales bledne zapytanie\r\n");
                w.flush();
            } catch (IOException ex1) {
                Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
                w.close();
                r.close();
                s.close();
            } catch (SQLException ex) {
                try {
                    w.write("Nie udalo sie zamknac podlaczenia do bazy danych\r\n");
                    w.flush();
                } catch (IOException ex1) {
                    Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (IOException ex) {
                Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void zrobCosZwynikiem(ResultSet wynik) throws SQLException {
        try {
            w.write("Co chcesz wyswietlic?\r\n");
            w.write("p - pokaz normalnie\r\n");
            w.write("x - xml\r\n");
            w.write("c - csv\r\n");
            w.flush();

            Boolean wybrano = false;
            String wybor = null;
            String separator = null;
            while (!wybrano) {//petla az uzytkowni nie wybierze
                w.write("Wybierz\r\n");
                w.flush();
                wybor = r.readLine();
                wybor = wybor.toLowerCase().trim();
                switch (wybor) {
                    case "c":
                        w.write("Podaj separator: \r\n");
                        w.flush();
                        separator = r.readLine();
                    case "p":
                    case "x":
                        wybrano = true;
                }
            }

            //pobieramy ilosc kolumn
            //http://stackoverflow.com/a/2614434/1227925
            ResultSetMetaData rsmd = wynik.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            if (wybor.equals("x")) {
                //jezeli xml to musimy go oplesc jakims tagiem
                w.write("<root>\r\n");
                
            }

            while (wynik.next()) { //przechodzimy po wszystkich wynikach
                switch (wybor) {
                    case "c":
                        for (int i = 1; i <= columnsNumber; i++) { //przechodzimy po kolumnahc
                            w.write("\"" + wynik.getString(i) + "\"" + separator); //wyswietlamy kazda z kolumn z cudzyslowami i ustalonym separatorem
                        }
                        w.write("\r\n"); //robimy nowy wirsz
                        break;
                    case "p":
                        for (int i = 1; i <= columnsNumber; i++) { //przechodzimy po kolumnahc
                            w.write(wynik.getString(i) + "\t"); //wyswietlamy kazda z kolumn
                        }
                        w.write("\r\n"); //robimy nowy wirsz
                        break;
                    case "x":
                        for (int i = 1; i <= columnsNumber; i++) { //przechodzimy po kolumnahc
                            w.write("<" + rsmd.getColumnName(i) + ">"); //dodajemy tag kolumny
                            w.write(wynik.getString(i)); //wyswietlamy kazda z kolumn
                            w.write("</" + rsmd.getColumnName(i) + ">"); //dodajemy tag kolumny
                        }
                        w.write("\r\n"); //robimy nowy wirsz
                        break;
                }
            }

            if (wybor.equals("x")) {
                //jezeli xml to musimy go oplesc jakims tagiem
                w.write("</root>\r\n");
            }
            w.flush();
        } catch (IOException ex) {
            Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //ta metoda zwraca nam obiek wyniku zapytania
    protected ResultSet noweZapytanieZlozone(String input) throws SQLException, IOException {
        PreparedStatement prepStatement = connection.prepareStatement(input); //tworzymy nowe zapytanie z paramterami

        //musimy policzyc wystapienia znakow zapytania aby wiedziec ile parametrow wstawic
        //http://stackoverflow.com/a/19962695/1227925
        int count = input.length() - input.replace("?", "").length();
        Integer i = 1;
        while (count > 0) {
            w.write("Podaj " + i.toString() + " parametr: \r\n");
            w.flush();
            String parametr = r.readLine();//pobieramy parametr od uzytkownia
            prepStatement.setString(i, parametr); //wstawiamy wartosc parametru w jego miejsce
            count--; //zmniejszamy ilosc potrzebnych paramtrow
            i++; //zwiekszamy identyfikator parametru
        }
        return prepStatement.executeQuery(); //pobranie tego co wychodzi z zapytania
    }

    //ta metoda zwraca nam obiek wyniku zapytania
    protected ResultSet noweZapytanieProste(String input) throws SQLException {
        Statement zapytanie = connection.createStatement(); //robimy nowy obiek zapytania
        return zapytanie.executeQuery(input); //wykonujemy kwerende
    }

    protected void wykonajZapytanie(String input) throws SQLException, IOException {
        Statement zapytanie = connection.createStatement(); //robimy nowy obiek zapytania
        zapytanie.executeQuery(input); //wykonujemy kwerende
        Integer ileZmienionych = zapytanie.getUpdateCount(); //pobieramy informacje ile wierszy uleglo zmianie
        w.write("Wykonano zapytanie, zmieniono " + ileZmienionych.toString() + "\r\n");
        w.flush();
    }

    protected void konfiguruj(String plikKonfiguracyjny) throws IOException {
        try (FileInputStream in = new FileInputStream(plikKonfiguracyjny)) {
            //wczytywanie ustawien na bazie artykulu: http://docs.oracle.com/javase/tutorial/essential/environment/properties.html
            Properties konfiguracja = new Properties();
            konfiguracja.load(in);

            //wczytujemy konfiguracje bazy danych
            this.host = (String) konfiguracja.get("host");
            this.port = Integer.parseInt((String) konfiguracja.get("port"));
            this.user = (String) konfiguracja.get("user");
            this.pass = (String) konfiguracja.get("pass");
            this.dbname = (String) konfiguracja.get("dbname");
        } catch (FileNotFoundException ex) {
            w.write("Brakuje pliku konfiguracyjnego do bazy danych\r\n");
            w.flush();
        } catch (IOException ex) {
            w.write("Wystapil blad odczytu z pliku konfiguracji bazy danych\r\n");
            w.flush();
        }

    }

}
