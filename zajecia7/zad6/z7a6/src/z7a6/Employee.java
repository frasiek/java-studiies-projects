/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z7a6;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

/**
 *
 * @author frasiek
 */
public class Employee implements SQLData {
    public int emp_no;
    public String birth_date;
    public String first_name;
    public String last_name;
    public String gender;
    public String hire_date;
    private String sql_type;
    
    public String getSQLTypeName() {
        return sql_type;
    }

    public void readSQL(SQLInput stream, String type)
        throws SQLException {
        sql_type = type;
        emp_no = stream.readInt();
        birth_date = stream.readString();
        first_name = stream.readString();
        last_name = stream.readString();
        gender = stream.readString();
        hire_date = stream.readString();
    }

    public void writeSQL(SQLOutput stream)
        throws SQLException {
        stream.writeInt(emp_no);
        stream.writeString(birth_date);
        stream.writeString(first_name);
        stream.writeString(last_name);
        stream.writeString(gender);
        stream.writeString(hire_date);
    }
}