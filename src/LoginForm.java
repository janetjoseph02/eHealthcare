import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;
    private User user;

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(username,password);

                if (user != null)  {
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this,"Username or Password Invalid", "Try again",JOptionPane.ERROR_MESSAGE);
                }
            }

            public User user;
            private User getAuthenticatedUser(String username, String password) {
                DBconnect connectnow = new DBconnect();
                Connection connectdb =connectnow.getConnection();

                User user = null;

                final String url = "jdbc:sqlserver://localhost/databaseName=proj;user=sa;password=sa9";
                final String USERNAME = "root";
                final String PASSWORD = "";

                try{
                    Connection conn = DriverManager.getConnection(url,USERNAME,PASSWORD);
                    //connected to database successfully....

                    Statement stat = conn.createStatement();
                    String sql = "select * from user";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, USERNAME);
                    preparedStatement.setString(1, PASSWORD);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        user = new User();
                        user.name = resultSet.getString("name");
                        user.username = resultSet.getString("username");
                        user.phone = resultSet.getString("phone");
                        user.address = resultSet.getString("address");
                        user.password = resultSet.getString("password");
                        user.patientId = resultSet.getString("patientId");
                        user.age = resultSet.getString("age");
                        user.gender = resultSet.getString("gender");
                        user.bloodgroup = resultSet.getString("bloodgroup");
                        user.anyMajorDisease = resultSet.getString("anyMajorDisease");


                    }
                    stat.close();
                    conn.close();


                }catch(Exception e){
                    e.getMessage();
                }
                return user;
            }
        });
        btnCancel.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;
        if(user != null) {
            System.out.println("Successful Authentication of : + user.name");
            System.out.println("                    Username : + user.username");
            System.out.println("                       Phone : + user.phone");
            System.out.println("                     Address : + user.address");

        }
        else {
            System.out.println(" Authentication cancelled ");

        }
    }
}
