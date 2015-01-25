/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z7a4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author frasiek
 */
public class Z7a4 {

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
            PreparedStatement prepStatement = connection.prepareStatement("select * from jps_employees where emp_no < ?");
            prepStatement.setString(1, args[0]);
            ResultSet resultSet1 = prepStatement.executeQuery();
            while(resultSet1.next()){
                System.out.println(resultSet1.getString(1));
            }
            resultSet1.close();
            System.out.println("--------------");
            Statement statement = connection.createStatement();
            ResultSet resultSet2 = statement.executeQuery("select * from jps_employees where emp_no < "+args[0]);
            while(resultSet2.next()){
                System.out.println(resultSet2.getString(1));
            }
            resultSet2.close();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        Z7a4.test2(args);
    }
    
    public static void test2(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("Drugi test!!!!!!!!!!!!!!!!!!!!!!!!");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, pass);
            if (connection.isValid(2)) {
                System.out.println("Połączenie zakończone sukcesem");
            }
            PreparedStatement prepStatement = connection.prepareStatement("select * from jps_employees where last_name = ?");
            prepStatement.setString(1, args[0]);
            ResultSet resultSet1 = prepStatement.executeQuery();
            while(resultSet1.next()){
                System.out.println(resultSet1.getString(1));
            }
            resultSet1.close();
            System.out.println("--------------");
            Statement statement = connection.createStatement();
            ResultSet resultSet2 = statement.executeQuery("select * from jps_employees where last_name = "+args[0]);
            while(resultSet2.next()){
                System.out.println(resultSet2.getString(1));
            }
            resultSet2.close();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
}
