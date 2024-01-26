package JavaDemo.Integrations.Postgres;
import JavaDemo.Integrations.Logger.Log;

import java.sql.*;

public class DbFunction {
    public static Connection DBconnection(String username,String dbname,String password ) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://komodo-nonprod.cwudgyhgbdjo.ap-southeast-1.rds.amazonaws.com/"+dbname, username, password);
        if(c!=null){
            Log.info("connected");
        }else{
            Log.info("Connection broken");
        }

        } catch (Exception e) {
        }
        return c;
    }
    public void read_data (Connection conn, String table_name ){
        Statement stat;
        ResultSet rs = null;
//        ResultSet rs2 = null;
         try{
            String query = String.format("select * from %s",table_name);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);

             ResultSetMetaData metadata = rs.getMetaData();
             int columnCount = metadata.getColumnCount();
             while (rs.next()) {
                 String row = "";
                 for (int i = 1; i <= columnCount; i++) {
                     row += rs.getString(i) + ", ";
                 }
                 System.out.println(row);
             }

         }catch(Exception e){
             Log.info(String.valueOf(e));
        }
    }

}


