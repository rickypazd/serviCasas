package MODELO.usuario;

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
import java.sql.Timestamp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class USUARIO {

    private int ID;
    private String NOMBRE;
    private String APELLIDOS;
    private String CI;
    private Date FECHA_NAC;
    private String USUARIO;
    private String CONTRASENA;
    private String ID_FACE;
    private String ID_GMAIL;
    private Conexion con = null;
    private String TBL = "usuario";

    public USUARIO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public." + TBL + "(\n"
                + "	nombre, apellidos, ci, fecha_nac, usuario, contrasena)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, getNOMBRE());
        ps.setString(2, getAPELLIDOS());
        ps.setString(3, getCI());
        ps.setTimestamp(4, new Timestamp(getFECHA_NAC().getTime()));//TODO:
        ps.setString(5, getUSUARIO());
        ps.setString(6, getCONTRASENA());
        ps.execute();
        consulta = "select last_value from " + TBL + "_id_seq ";
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
    public JSONObject getByUsrPass(String usr, String pass) throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar\n"
                + "where ar.usuario=? and ar.contrasena=?";
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, usr);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj = parseJson(rs);
            obj.put("exito", "si");
        } else {
            obj.put("exito", "no");
        }
        ps.close();
        rs.close();
        return obj;
    }



    public JSONArray gelAll() throws SQLException, JSONException {
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

    private JSONObject parseObj;

    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("id", rs.getInt("id"));
        parseObj.put("nombre", rs.getString("nombre") != null ? rs.getString("nombre") : "");
        parseObj.put("apellidos", rs.getString("apellidos") != null ? rs.getString("apellidos") : "");
        parseObj.put("ci", rs.getString("ci") != null ? rs.getString("ci") : "");
        parseObj.put("fecha_nac", rs.getString("fecha_nac") != null ? rs.getString("fecha_nac") : "");
        parseObj.put("usuario", rs.getString("usuario") != null ? rs.getString("usuario") : "");
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("id", getID());
        obj.put("nombre", getNOMBRE());
        obj.put("apellidos", getAPELLIDOS());
        obj.put("ci", getCI());
        obj.put("fecha_nac", getFECHA_NAC());
        obj.put("usuario", getUSUARIO());
        return obj;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getAPELLIDOS() {
        return APELLIDOS;
    }

    public void setAPELLIDOS(String APELLIDOS) {
        this.APELLIDOS = APELLIDOS;
    }

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    public Date getFECHA_NAC() {
        return FECHA_NAC;
    }

    public void setFECHA_NAC(Date FECHA_NAC) {
        this.FECHA_NAC = FECHA_NAC;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getCONTRASENA() {
        return CONTRASENA;
    }

    public void setCONTRASENA(String CONTRASENA) {
        this.CONTRASENA = CONTRASENA;
    }

    public String getID_FACE() {
        return ID_FACE;
    }

    public void setID_FACE(String ID_FACE) {
        this.ID_FACE = ID_FACE;
    }

    public String getID_GMAIL() {
        return ID_GMAIL;
    }

    public void setID_GMAIL(String ID_GMAIL) {
        this.ID_GMAIL = ID_GMAIL;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public String getTBL() {
        return TBL;
    }

    public void setTBL(String TBL) {
        this.TBL = TBL;
    }

    public JSONObject getParseObj() {
        return parseObj;
    }

    public void setParseObj(JSONObject parseObj) {
        this.parseObj = parseObj;
    }

   
}
