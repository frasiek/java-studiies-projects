package jdbc; //nazwa pakietu aby moje klasy nie wpadaly na inne

import java.io.IOException; //import wyjatku odczytu zapisu
import java.net.ServerSocket; //import klasy serwera soketow

public class Jdbc { //deklaracja klasy Jdbc

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException { //deklaracja i definicja glownej metody programu uruchamianej na starcie, w parametrach mamy zmienne z konsoli, i ta akcja moze rzucic wyjatkiem ioexceptiom
        ServerSocket server = new ServerSocket(Integer.parseInt(args[1])); //stworzenie obiektu ServerSocket, w parametrze dostaje port na ktorym ma nasluchiwac na polaczenia
        while (true) { //niekonczaca sie petla ktora bedzie czekac na polaczenie kolejnych klientow
            /**
             * Utworzenie obiektu klasy baza - ta klasa obsluguje zapytania.
             * server.accept() - metoda ktora wstrzymuje program do momentu nadejscia nowego polaczenia na wczesniej wybranym
             * porcie i w momencie gdy takie polaczenie nadejdzie, zwraca nowy obiek Socket
             */
            Baza baza = new Baza(args[0], server.accept()); 
            baza.start();//uruchomienie nowego watku bazy
        }
    }

}
