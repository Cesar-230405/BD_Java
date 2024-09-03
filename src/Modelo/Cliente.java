/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cesar
 */
public class Cliente extends Persona{
    private String nit;
    Conexion cn;
    
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
    public DefaultTableModel leer(){
    DefaultTableModel tabla = new DefaultTableModel();
    try{
        cn = new Conexion();
        cn.abrir_conexion();
        String query;
        query = "Select id_cliente as id,nit,nombres,apellidos,direccion,telefono,fecha_nacimiento from clientes;";
        ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
        
        String encabezado[] = {"ID, NIT, NOMBRES, APELLIDOS, DIRECCION, TELEFONO, FN"};
        tabla.setColumnIdentifiers(encabezado);
        String datos []=new String[7];
        while(consulta.next()){
        datos[0] = consulta.getString("id");
        datos[1] = consulta.getString("nit");
        datos[2] = consulta.getString("nombres");
        datos[3] = consulta.getString("apellidos");
        datos[4] = consulta.getString("direccion");
        datos[5] = consulta.getString("telefono");
        datos[6] = consulta.getString("fecha_nacimiento");
        tabla.addRow(datos);
        }
        cn.cerrar_conexion();
    }catch(SQLException ex){
        cn.cerrar_conexion();
        System.out.println("Error: "+ ex.getMessage());
    }
    return tabla;
    }
    @Override
    public void agregar(){
       try{
           PreparedStatement parametro;
           String query = "INSERT INTO clientes(nit,nombres,apellidos,direccion,telefono,fecha_nacimiento)VALUES(?,?,?,?,?,?);";
           cn = new Conexion();
           cn.abrir_conexion();
           parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
           parametro.setString(1, this.getNit());
           parametro.setString(2, this.getNombres());
           parametro.setString(3, this.getApellidos());
           parametro.setString(4, this.getDireccion());
           parametro.setString(5, this.getTelefono());
           parametro.setString(6, this.getFecha_nacimiento());
           
           int ejecutar = parametro.executeUpdate();
           cn.cerrar_conexion();
           JOptionPane.showMessageDialog(null,Integer.toString(ejecutar) + "Registro Ingresado","Agregar",JOptionPane.INFORMATION_MESSAGE);
       }catch(HeadlessException | SQLException ex){
           System.out.println("Error..."+ ex.getMessage());
       }
    }
}
