import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnect {

    static Connection connection = null;

    public Connection getConnection(){
        String databaseName = "mysql";
        String url = "jdbc:mysql://localhost:3306/" +databaseName;

        String username = "root";
        String password = "021021";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("Database connected!");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Database connection failure!");
        }
        return connection;

    }

}
