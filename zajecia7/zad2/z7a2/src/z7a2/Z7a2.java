/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z7a2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author frasiek
 */
public class Z7a2 {

     /* dane dostepowe do bazy danych */
    private final static String host = "localhost";
    private final static Integer port = 3306;
    private final static String user = "root";
    private final static String pass = "root";
    private final static String dbname = "java";

    /* aby nie zaciemniac kodu - wyjatki sa wyrzucane poza program */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, pass);
            if (connection.isValid(2)) {
                System.out.println("Połączenie zakończone sukcesem");
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
}
