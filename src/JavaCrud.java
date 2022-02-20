import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.*;

public class JavaCrud {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtPrice;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtId;
    private JTextField txtQty;
    private JButton searchButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection connection;
    PreparedStatement preparedStatement;

    public JavaCrud() {
        Connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name,price,qty;

                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();

                try {
                    preparedStatement = connection.prepareStatement("insert into prod(name,price,qty)values(?,?,?)");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, price);
                    preparedStatement.setString(3, qty);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null,"successfully added");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String pid = txtId.getText();
                    preparedStatement = connection.prepareStatement("select name,price,qty from prod where id = ?");
                    preparedStatement.setString(1, pid);
                    ResultSet rs = preparedStatement.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtQty.setText(qty);
                    }
                    else
                    {
                        txtName.setText("");
                        txtPrice.setText("");
                        txtQty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");

                    }
                }

                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid,name,price,qty;

                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();
                pid = txtId.getText();

                try {

                    preparedStatement = connection.prepareStatement("update prod set name = ?,price = ?,qty = ? where id = ?");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, price);
                    preparedStatement.setString(3, qty);
                    preparedStatement.setString(4, pid);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "successfully update");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtId.setText("");
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bid;

                bid = txtId.getText();


                try {
                    preparedStatement = connection.prepareStatement("delete from prod where id = ?");
                    preparedStatement.setString(1, bid);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted successfully");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtId.setText("");
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });
    }


    public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/products", "root", "4245");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }


}
