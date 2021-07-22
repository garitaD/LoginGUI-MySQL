package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author DGB
 */
public class SqlUsuarios extends Conexion {
    //Clase para realizar las inserciones y las consultas a la base de datos

    public boolean registrar(Usuarios usr) {
        //Insercion a mysql
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO usuarios (usuario, password, nombre, correo, id_tipo) VALUES(?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(0, sql);
            ps.setString(1, usr.getUsuario());
            ps.setString(2, usr.getPassword());
            ps.setString(3, usr.getNombre());
            ps.setString(4, usr.getCorreo());
            ps.setInt(5, usr.getId_tipo());
            ps.execute();
            
            return true;
        } catch (SQLException ex) {
            System.err.println("Error in SqlUsuarios " + ex);
            //Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int existeUsuario(String usuario) {
        //Insercion a mysql
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT count(id) FROM usuarios WHERE usuario =?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            //al ser una cosulta y nos va a regresar datos se envia todo al resultset
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            System.err.println("Error in SqlUsuarios" + ex);
            //Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);

            return 1;
        }
    }

    public boolean esEmail(String correo) {
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        //Se le pasa al matcher que se encarga de hacer la validacion del string con el patron que estamos indicando
        Matcher mather = pattern.matcher(correo);

        return mather.find();
    }

    public boolean login(Usuarios usr) {
        //Insercion a mysql
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, usuario, password, nombre, id_tipo FROM usuarios WHERE usuario =?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            //al ser una cosulta y nos va a regresar datos se envia todo al resultset
            rs = ps.executeQuery();

            if (rs.next()) {

                if (usr.getPassword().equals(rs.getString(3))) {
                    String sqlUpdate = "UPDATE usuarios SET last_session =? "
                            + "WHERE id=?";
                    
                    ps = con.prepareStatement(sqlUpdate);
                    ps.setString(1, usr.getLast_session());
                    ps.setInt(2, rs.getInt(1));
                    ps.execute();
                            

                    //se pasan los datos al modelo
                    usr.setId(rs.getInt(1));
                    usr.setNombre(rs.getString(4));
                    usr.setId_tipo(rs.getInt(5));
                    return true;
                } else {
                    return false;
                }
            }
            return false;

        } catch (SQLException ex) {
            System.err.println("Error in SqlUsuarios" + ex);
            //Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
