/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author DGB
 */
public class Conexion {
    
    private final String base ="usuarios";
    private final String user ="root";
    private final String password ="1542";
    private final String url ="jdbc:mysql://localhost:3306/"+base;
    //private final String url ="jdbc:mysql://localhost:3306/"+base+"?autoReconnect=true&useSSL=false";
    private Connection con = null;
    
    public Connection getConexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(url, user, password);
            
        } catch (ClassNotFoundException ex) {
            System.err.println("Error in Conexion"+ex);
            //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.err.println("Error in Conexion"+ex);
            //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }

}
