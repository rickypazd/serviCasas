package MODELO;

import Conexion.Conexion;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CASA {

    private int id;
    private int id_usuario;
    private double precio;
    private int cant_dormitorios;
    private int cant_banhos;
    private int metros2;
    private String descripcion;
    private double lat;
    private double lng;
    private String direccion;
    private int tipo_public;
    private int id_tipo_propiedad;

    private Conexion con = null;

    private String TBL = "casa";

    public CASA(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO " + TBL + "(\n"
                + "precio, cant_dormitorios, cant_banhos, metros2, descripcion, lat, lng, direccion, tipo_public, id_usuario, id_tipo_propiedad)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setDouble(1, getPrecio());
        ps.setInt(2, getCant_dormitorios());
        ps.setInt(3, getCant_banhos());
        ps.setInt(4, getMetros2());
        ps.setString(5, getDescripcion());
        ps.setDouble(6, getLat());
        ps.setDouble(7, getLng());
        ps.setString(8, getDireccion());
        ps.setInt(9, getTipo_public());
        ps.setInt(10, getId_usuario());
        ps.setInt(11, getId_tipo_propiedad());//TODO
        ps.execute();
        consulta = "select last_value from "+TBL+"_id_seq ";
        ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("last_value");
        }
        rs.close();
        ps.close();
        return id;
    }

    public JSONObject getById(int id) throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar\n"
                + "where ar.id=?";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj = parseJson(rs);
        } else {
            obj.put("exito", "no");
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray getAll() throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj = parseJson(rs);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }
    public JSONArray getFull() throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        COSTO costo = new COSTO(con);
        while (rs.next()) {
            obj = new JSONObject();
            obj = parseJson(rs);
            obj.put("arrCostos",costo.getByIdCasa(obj.getInt("id")));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    private JSONObject parseObj;

    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("id", rs.getInt("id"));
        parseObj.put("id_usuario", rs.getInt("id_usuario"));
        parseObj.put("precio", rs.getDouble("precio"));
        parseObj.put("cant_dormitorios", rs.getInt("cant_dormitorios"));
        parseObj.put("cant_banhos", rs.getInt("cant_banhos"));
        parseObj.put("metros2", rs.getInt("metros2"));
        parseObj.put("descripcion", rs.getString("descripcion"));
        parseObj.put("lat", rs.getDouble("lat"));
        parseObj.put("lng", rs.getDouble("lng"));
        parseObj.put("direccion", rs.getString("direccion") != null ? rs.getString("direccion") : "");
        parseObj.put("tipo_public", rs.getInt("tipo_public"));
        parseObj.put("id_tipo_propiedad", rs.getInt("id_tipo_propiedad"));
        TIPO_PROPIEDAD tp = new TIPO_PROPIEDAD(con);
        parseObj.put("tipo_propiedad",tp.getById(rs.getInt("id_tipo_propiedad")));
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getId());
        obj.put("id_usuario", getId_usuario());
        obj.put("precio", getPrecio());
        obj.put("cant_dormitorios", getCant_dormitorios());
        obj.put("cant_banhos", getCant_banhos());
        obj.put("metros2", getMetros2());
        obj.put("descripcion", getDescripcion());
        obj.put("lat", getLat());
        obj.put("lng", getLng());
        obj.put("direccion", getDireccion());
        obj.put("tipo_public", getTipo_public());
        obj.put("id_tipo_propiedad", getId_tipo_propiedad());
        TIPO_PROPIEDAD tp = new TIPO_PROPIEDAD(con);
        obj.put("tipo_propiedad",tp.getById(getId_tipo_propiedad()));
        return obj;
    }

        
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCant_dormitorios() {
        return cant_dormitorios;
    }

    public void setCant_dormitorios(int cant_dormitorios) {
        this.cant_dormitorios = cant_dormitorios;
    }

    public int getCant_banhos() {
        return cant_banhos;
    }

    public void setCant_banhos(int cant_banhos) {
        this.cant_banhos = cant_banhos;
    }

    public int getMetros2() {
        return metros2;
    }

    public void setMetros2(int metros2) {
        this.metros2 = metros2;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTipo_public() {
        return tipo_public;
    }

    public void setTipo_public(int tipo_public) {
        this.tipo_public = tipo_public;
    }

    public String getTBL() {
        return TBL;
    }

    public void setTBL(String TBL) {
        this.TBL = TBL;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public JSONObject getParseObj() {
        return parseObj;
    }

    public void setParseObj(JSONObject parseObj) {
        this.parseObj = parseObj;
    }

    public int getId_tipo_propiedad() {
        return id_tipo_propiedad;
    }

    public void setId_tipo_propiedad(int id_tipo_propiedad) {
        this.id_tipo_propiedad = id_tipo_propiedad;
    }

 
    
}
