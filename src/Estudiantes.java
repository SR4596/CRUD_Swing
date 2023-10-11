import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Estudiantes extends JFrame{
    private JPanel panel;
    private JTextField idText;
    private JTextField nombreText;
    private JTextField apellidoText;
    private JTextField edadText;
    private JButton consultarBtn;
    private JButton ingresarBtn;
    private JTextField telefonoText;
    private JTextField carreraText;
    private JList lista;
    private JLabel label;
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet result;
    DefaultListModel mod = new DefaultListModel();


public Estudiantes(){
    consultarBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                listar();
            }catch (SQLException ex){
                throw new RuntimeException(ex);
            }
        }

    });
    ingresarBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        try{
            insertar();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        }
    });
}
 public void listar() throws SQLException{
    conectar();
    lista.setModel(mod);
    st = con.createStatement();
    result  =st.executeQuery("Select id,nombre, apellido, edad,telefono, carrera");
    mod.removeAllElements();
    while (result.next()){
        mod.addElement(result.getString(1)+ " " + result.getString(2)+" "+ result.getString(3));
    }

 }
 public void insertar() throws SQLException {
    conectar();
    ps = con.prepareStatement("INSERT INTO estudiante VALUES (?,?,?,?,?,?)");
    ps.setInt(1,Integer.parseInt(idText.getText()));
    ps.setString(2,nombreText.getText());
    ps.setString(3,apellidoText.getText());
    ps.setInt(4,Integer.parseInt(edadText.getText()));
    ps.setInt(5,Integer.parseInt(telefonoText.getText()));
    ps.setString(6,carreraText.getText());
    if(ps.executeUpdate()>0){
        lista.setModel(mod);
        mod.removeAllElements();
        mod.addElement("! Ingreso exitoso !");

        idText.setText("");
        nombreText.setText("");
        apellidoText.setText("");
        edadText.setText("");
        telefonoText.setText("");
        carreraText.setText("");
    }
 }
public static void main (String[] args){
    Estudiantes g =new Estudiantes();
    g.setContentPane(new Estudiantes().panel);
    g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    g.setVisible(true);
    g.pack();
}
    public void conectar(){
        try {
            con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pruebaacl","root","seba6128.");
            System.out.println("Conectado");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
