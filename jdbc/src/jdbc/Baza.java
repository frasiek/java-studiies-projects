package jdbc;

/**
 * importy niezbednych pakietow
 */
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

public class Baza extends Thread { //definicja klasy baza, ktora bedzie naszym watkiem, dzieki extendowaniu z Thread

    /* dane dostepowe do bazy danych */
    private String host; //przechowuje host na ktorym dziala baza danych
    private Integer port; //przechowuje port niezbedny do polaczenia z baza
    private String user; //przechowuje uzytkownika na ktorego laczymy sie do bazy danych
    private String pass; //przechowuje haslo
    private String dbname; //przechowuje nazwe bazy danych
    private Connection connection = null; //przechowuje obiekt polaczenia z baza danych
    private BufferedReader r; //przechowuje czytacz z socketu - moze czytac co uzytkownik wpisal
    private BufferedWriter w; //przechowuje pisacz do socketu - moze pisac do uzytkownika
    private Socket s; //przechowuje socket utworzony przez socet.accept()

    public Baza(String plikKonfiguracyjny, Socket s) throws IOException { //konstuktor klasy - uruchamiany automatycznie po stworzeniu obiektu za pomoca new
        this.s = s; //przypisanie socketu do zmiennej
        this.konfiguruj(plikKonfiguracyjny); //wczytuje konfiguracje z pliku
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname; //utworzenie url'a potrzebnego do polaczenia z baza danych

        try {
            connection = DriverManager.getConnection(url, user, pass); //otwarcie nowego polaczenia z baza danych
            r = new BufferedReader(new InputStreamReader(this.s.getInputStream(), "utf-8")); //tworze nowy czytacz z socketu do komunikacji z uzytkownikiem
            w = new BufferedWriter(new OutputStreamWriter(this.s.getOutputStream())); //tworze nowy pisacz do socketu do komunikacji z uzytkownikiem
        } catch (SQLException ex) {
            try {
                w.write("Nie udalo sie podlaczyc do bazy danych\r\n"); //obsluga bledow
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

    public void run() { //uruchamoa sie po uruchomieniu watku za pomoca metody start()
        try {
            //petla czekajaca na dane wpisane przez uzytownika
            String input = null;
            while (true) {
                w.write("Podaj zapytanie: \r\n"); //piszemy do uzytkownika z prosba o podanie zapytania
                w.flush(); //to jest po to aby wyslac wiadomosc przed wypelnieniem calego buffora - wymusza wyslanie wiadomosci
                input = r.readLine(); //przeczytanie zapytania uzytkownika
                if (input.toLowerCase().trim().equals("q")) { //jezeli uzytkownik wpisal q lub Q lub "  Q  " to wychodzimy z tej petli
                    break;//do puki uzytkownik nie wpisze q
                }
                /**
                 * Linia ponizej sprawdza jakiego typu to jest zapytanie
                 * jezeli to jest select lub show to wykonujemy inna operacje niz dla kwerend INSER UPDATE DELETE REPLACE
                 * dlatego ze select zwraca wyniki a tamte tylko ilosc zmodyfikowanych rekordow
                 */
                if (input.toLowerCase().startsWith("select") || input.toLowerCase().startsWith("show")) { //wykonujemy zapytanie select
                    Boolean set = false; //inicjalizacja zmiennych pomocniczyny
                    ResultSet wynik = null;//inicjalizacja zmiennych pomocniczyny
                    if (input.contains("?")) { //jezeli zapytanie zawiera znak zapytania to wykonujemy zapytanie zlozone
                        wynik = this.noweZapytanieZlozone(input); //uruchamiamy metode zapytania zlozonego
                    } else {
                        wynik = this.noweZapytanieProste(input); //wykonujemy zapytanie do bazy
                    }
                    this.zrobCosZwynikiem(wynik); //ta metoda wyswietla nam wynik - przesyla go socketem do uzytkownika
                } else {
                    this.wykonajZapytanie(input); //wykonuje zapytania INSERT UPDATE REPLACE DELETE
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
                connection.close(); //zamkniecie polaczenia z baza danych
                w.close(); //zamkniecie pisacza do uzytkownika po sekecie
                r.close(); //jak wyzej ale z czytaczem
                s.close(); //zamkniecie socketu do uzytkownika
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

    /**
     * ta metoda wyswietla wynika w odpowiednim formacie
     * @param wynik - to co zwrocil nam mysql
     * @throws SQLException moze rzucic wyjatkiem jezeli zapytanie bylo nie poprawne
     */
    protected void zrobCosZwynikiem(ResultSet wynik) throws SQLException {
        try {
            w.write("Co chcesz wyswietlic?\r\n"); //wypisanie komunikato do uzytkownika
            w.write("p - pokaz normalnie\r\n");
            w.write("x - xml\r\n");
            w.write("c - csv\r\n");
            w.flush();

            Boolean wybrano = false; //utworzenie zmiennych pomocniczych
            String wybor = null; //utworzenie zmiennych pomocniczych
            String separator = null; //utworzenie zmiennych pomocniczych
            while (!wybrano) {//petla az uzytkowni nie wybierze
                w.write("Wybierz\r\n");
                w.flush();
                wybor = r.readLine(); //czytamy to co uzytkownik nam wpisal
                wybor = wybor.toLowerCase().trim(); //obcinamy niepotzebne znaki
                switch (wybor) { //zaleznie od tego co jest w zmiennej wybor, wybieramy jakis format ktory wypisze sie uzytkownikowi
                    case "c": //jezeli uzytkownik wpisal c to bedzie to plik csv
                        w.write("Podaj separator: \r\n"); //dla plikow csv pobieramy jeszcze separator pol
                        w.flush();
                        separator = r.readLine(); //czytamy jaki separator podal uzytkownik
                    case "p"://dla p - to jest czysty output
                    case "x"://dla x - to jest format xml
                        wybrano = true;
                }
            }

            //pobieramy ilosc kolumn
            //http://stackoverflow.com/a/2614434/1227925
            ResultSetMetaData rsmd = wynik.getMetaData(); //pobranie informacji dodatkowych o pobranych danych
            int columnsNumber = rsmd.getColumnCount(); //pobranie ilosci kolum bedacych w wyniku

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
        try (FileInputStream in = new FileInputStream(plikKonfiguracyjny)) { //try with resources - ta konstrukcja dba o zamkniecie otwartych w niej strumieni danych
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
