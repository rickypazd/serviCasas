/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLADOR;

import Conexion.Conexion;
import Firebasse.DataToSend;
import Firebasse.Notificador;
import MODELO.CASA;
import MODELO.COMENTARIO;
import MODELO.COSTO;
import MODELO.FOTOS;
import MODELO.TIPO_PROPIEDAD;
import MODELO.usuario.USUARIO;

import UTILES.RESPUESTA;
import UTILES.URL;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.nio.file.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author RICKY
 */
@MultipartConfig
@WebServlet(name = "adminController", urlPatterns = {"/admin/adminController"})
public class adminController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //<editor-fold defaultstate="collapsed" desc="CONFIGURACIONES">
        Conexion con = new Conexion(URL.db_usr, URL.db_pass);
        con.Transacction();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        String evento = request.getParameter("evento");
        String tokenAcceso = request.getParameter("TokenAcceso");
        boolean retornar = true;
        String html = "";
//</editor-fold>
        if (tokenAcceso.equals(URL.TokenAcceso)) {
            switch (evento) {
                //<editor-fold defaultstate="collapsed" desc="USUARIO">
                case "registrar_usuario":
                    html = registrar_usuario(request, con);
                    break;
                case "login":
                    html = login(request, con);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CASA">
                case "registrar_casa":
                    html = registrar_casa(request, con);
                    break;
                case "registrar_casa_complete":
                    html = registrar_casa_complete(request, con);
                    break;
                case "getall_casa":
                    html = getall_casa(request, con);
                    break;
                case "getfull_casa":
                    html = getfull_casa(request, con);
                    break;
                case "getbyid_casa":
                    html = getbyid_casa(request, con);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="COMENTARIO">
                case "registrar_comentario":
                    html = registrar_comentario(request, con);
                    break;
                case "getall_comentario":
                    html = getall_comentario(request, con);
                    break;
                case "getbyid_comentario":
                    html = getbyid_comentario(request, con);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="COSTO">
                case "registrar_costo":
                    html = registrar_costo(request, con);
                    break;
                case "getall_costo":
                    html = getall_costo(request, con);
                    break;
                case "getbyid_costo":
                    html = getbyid_costo(request, con);
                    break;
                //</editor-fold>   
                    //<editor-fold defaultstate="collapsed" desc="TIPO_PROPIEDAD">
                case "registrar_tipo_propiedad":
                    html = registrar_tipo_propiedad(request, con);
                    break;
                case "getall_tipo_propiedad":
                    html = getall_tipo_propiedad(request, con);
                    break;
                case "getbyid_tipo_propiedad":
                    html = getbyid_tipo_propiedad(request, con);
                    break;
                //</editor-fold>   
                //<editor-fold defaultstate="collapsed" desc="FOTOS">
                case "registrar_fotos":
                    html = registrar_fotos(request, con);
                    break;
                case "getall_fotos":
                    html = getall_fotos(request, con);
                    break;
                case "getbyid_fotos":
                    html = getbyid_fotos(request, con);
                    break;
                //</editor-fold> 
                case "descargar":
                    descargar(request, response, con);
                    retornar = false;
                    break;

                //<editor-fold defaultstate="collapsed" desc="ERRORES">
                default:
                    RESPUESTA resp = new RESPUESTA(0, "Servisis: No se encontro el parametro evento.", "Servicio no encontrado.", "{}");
                    html = resp.toString();
                    break;
            }
        } else {
            RESPUESTA resp = new RESPUESTA(0, "Servisis: Token de acceso erroneo.", "Token denegado", "{}");
            html = resp.toString();
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="RESPONSE">
        con.commit();
        con.Close();
        if (retornar) {
            response.getWriter().write(html);
        }
        //</editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void descargar(HttpServletRequest request, HttpServletResponse response, Conexion con) {
        try {
            String SRC = request.getParameter("nombre_arc");
            File fileToDownload = new File(SRC);
            FileInputStream in = new FileInputStream(fileToDownload);
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileToDownload.getName() + "\"");
            //String mimeType =  new FileTypeMap().getContentType(filePath); 
            response.setContentType(Files.probeContentType(Paths.get(SRC)));
            response.setContentLength(in.available());
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            out.flush();
            out.close();
            in.close();
        } catch (FileNotFoundException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "No se encontro el archibo.", "{}");
        } catch (IOException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "No se encontro el archibo.", "{}");
        }

    }

    //<editor-fold defaultstate="collapsed" desc="CASA">
    private String registrar_casa(HttpServletRequest request, Conexion con) {
        String nameAlert = "Casa";
        try {
            //int id= Integer.parseInt(request.getParameter("id"));
            int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cant_dormitorios = Integer.parseInt(request.getParameter("cant_dormitorios"));
            int cant_banhos = Integer.parseInt(request.getParameter("cant_banhos"));
            int metros2 = Integer.parseInt(request.getParameter("metros2"));
            String descripcion = request.getParameter("descripcion");
            double lat = Double.parseDouble(request.getParameter("lat"));
            double lng = Double.parseDouble(request.getParameter("lng"));
            String direccion = request.getParameter("direccion");
            int tipo_public = Integer.parseInt(request.getParameter("tipo_public"));
            CASA casa = new CASA(con);
            casa.setId_usuario(id_usuario);
            casa.setPrecio(precio);
            casa.setCant_dormitorios(cant_dormitorios);
            casa.setCant_banhos(cant_banhos);
            casa.setMetros2(metros2);
            casa.setDescripcion(descripcion);
            casa.setLat(lat);
            casa.setLng(lng);
            casa.setDescripcion(descripcion);
            casa.setTipo_public(tipo_public);
            int id = casa.Insertar();
            casa.setId(id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", casa.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String registrar_casa_complete(HttpServletRequest request, Conexion con) {
        String nameAlert = "Casa";
        try {
            //int id= Integer.parseInt(request.getParameter("id"));
            String strcasa = request.getParameter("casa");
            JSONObject objCasa = new JSONObject(strcasa);
           int id_usuario = objCasa.getInt("id_usr");
//            int id_usuario = 0;
            double precio = 0;
            JSONArray arrCostos = new JSONArray(objCasa.getString("costos"));
            int cant_dormitorios = objCasa.getInt("cant_dormitorios");
            int cant_banhos = objCasa.getInt("cant_banhos");
            int metros2 =objCasa.getInt("metros2");
            String descripcion = objCasa.getString("descripcion");
            double lat = objCasa.getDouble("lat");
            double lng = objCasa.getDouble("lng");
            String direccion = objCasa.getString("direccion");
            int tipo_public =objCasa.getInt("tipo_public");
            int id_tipo_propiedad =objCasa.getInt("id_tipo_propiedad");
            CASA casa = new CASA(con);
            casa.setId_usuario(id_usuario);
            casa.setPrecio(precio);
            casa.setCant_dormitorios(cant_dormitorios);
            casa.setCant_banhos(cant_banhos);
            casa.setMetros2(metros2);
            casa.setDescripcion(descripcion);
            casa.setLat(lat);
            casa.setLng(lng);
            casa.setDireccion(direccion);
            casa.setTipo_public(tipo_public);
            casa.setId_tipo_propiedad(id_tipo_propiedad);
            int id = casa.Insertar();
            casa.setId(id);
            JSONObject obj;
            COSTO costo;
            for (int i = 0; i < arrCostos.length(); i++) {
                obj= arrCostos.getJSONObject(i);
                if(obj.getBoolean("isActivo")){
                    costo= new COSTO(con);
                    costo.setId_casa(id);
                    costo.setTipo(obj.getInt("tipo"));
                    costo.setCosto(obj.getDouble("costo"));
                    costo.Insertar();
                }
                
            }
 
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", casa.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getall_casa(HttpServletRequest request, Conexion con) {
        String nameAlert = "Casa";
        try {
            CASA casa = new CASA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", casa.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
     private String getfull_casa(HttpServletRequest request, Conexion con) {
        String nameAlert = "Casa";
        try {
            CASA casa = new CASA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", casa.getFull().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getbyid_casa(HttpServletRequest request, Conexion con) {
        String nameAlert = "Casa";
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            CASA casa = new CASA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", casa.getById(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="COMENTARIO">

    private String registrar_comentario(HttpServletRequest request, Conexion con) {
        String nameAlert = "Comentario";
        try {
            //int id= Integer.parseInt(request.getParameter("id"));
            int id_casa = Integer.parseInt(request.getParameter("id_casa"));
            int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
            String comentario = request.getParameter("comentario");
            //String nombre= request.getParameter("nombre");
            COMENTARIO objcomentario = new COMENTARIO(con);
            objcomentario.setId_casa(id_casa);
            objcomentario.setId_usuario(id_usuario);
            objcomentario.setComentario(comentario);
            objcomentario.setFecha(new Date());
            int id = objcomentario.Insertar();
            objcomentario.setId(id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", objcomentario.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getall_comentario(HttpServletRequest request, Conexion con) {
        String nameAlert = "Comentario";
        try {
            COMENTARIO objcomentario = new COMENTARIO(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", objcomentario.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getbyid_comentario(HttpServletRequest request, Conexion con) {
        String nameAlert = "Comentario";
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            COMENTARIO objcomentario = new COMENTARIO(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", objcomentario.getById(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="COSTO">

    private String registrar_costo(HttpServletRequest request, Conexion con) {
        String nameAlert = "Costo";
        try {
            //int id= Integer.parseInt(request.getParameter("id"));
            int tipo = Integer.parseInt(request.getParameter("tipo"));
            double costo = Double.parseDouble(request.getParameter("costo"));
            int id_casa = Integer.parseInt(request.getParameter("id_casa"));
            COSTO costos = new COSTO(con);
            costos.setTipo(tipo);
            costos.setCosto(tipo);
            costos.setId_casa(id_casa);
            int id = costos.Insertar();
            costos.setId(id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", costos.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getall_costo(HttpServletRequest request, Conexion con) {
        String nameAlert = "Costo";
        try {
            COSTO costo = new COSTO(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", costo.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getbyid_costo(HttpServletRequest request, Conexion con) {
        String nameAlert = "Costo";
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            COSTO costo = new COSTO(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", costo.getById(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="TIPO_PROPIEDAD">

    private String registrar_tipo_propiedad(HttpServletRequest request, Conexion con) {
        String nameAlert = "Tipo de propiedad";
        try {
            //int id= Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            TIPO_PROPIEDAD tipo_propiedad = new TIPO_PROPIEDAD(con);
            tipo_propiedad.setNombre(nombre);
            int id = tipo_propiedad.Insertar();
            tipo_propiedad.setId(id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", tipo_propiedad.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getall_tipo_propiedad(HttpServletRequest request, Conexion con) {
        String nameAlert = "Tipo de propiedad";
        try {
           TIPO_PROPIEDAD tipo_propiedad = new TIPO_PROPIEDAD(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", tipo_propiedad.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getbyid_tipo_propiedad(HttpServletRequest request, Conexion con) {
        String nameAlert = "Tipo de propiedad";
        try {
            int id = Integer.parseInt(request.getParameter("id"));
           TIPO_PROPIEDAD tipo_propiedad = new TIPO_PROPIEDAD(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", tipo_propiedad.getById(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="FOTOS">

    private String registrar_fotos(HttpServletRequest request, Conexion con) {
        String nameAlert = "Fotos";
        try {
            //int id= Integer.parseInt(request.getParameter("id"));
            int id_casa = Integer.parseInt(request.getParameter("id_casa"));
            String url = request.getParameter("url");
            String nombre = request.getParameter("nombre");
            FOTOS fotos = new FOTOS(con);
            fotos.setId_casa(id_casa);
            fotos.setUrl(url);
            fotos.setNombre(nombre);
            fotos.setFecha(new Date());
            int id = fotos.Insertar();
            fotos.setId(id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", fotos.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getall_fotos(HttpServletRequest request, Conexion con) {
        String nameAlert = "Fotos";
        try {
            FOTOS fotos = new FOTOS(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", fotos.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getbyid_fotos(HttpServletRequest request, Conexion con) {
        String nameAlert = "Fotos";
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            FOTOS fotos = new FOTOS(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", fotos.getById(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="USUARIO">

    private String registrar_usuario(HttpServletRequest request, Conexion con) {
        try {
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String ci = request.getParameter("ci");
            String usuario = request.getParameter("usuario");
            String contrasena = request.getParameter("contrasena");
            String fecha_nac = request.getParameter("fecha_nac");
            usuario = usuario.trim();

            USUARIO usr = new USUARIO(con);
            usr.setNOMBRE(nombre);
            usr.setAPELLIDOS(apellidos);
            usr.setCI(ci);
            SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
            usr.setFECHA_NAC(form.parse(fecha_nac));//:todo parse fecha

            usr.setUSUARIO(usuario);
            usr.setCONTRASENA(contrasena);
            RESPUESTA resp = new RESPUESTA(1, "", "Usuario registrado con exito.", usr.Insertar() + "");
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar usuario.", "{}");
            return resp.toString();
        } catch (ParseException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir la fecha. Formato aceptado(dd-MM-yyyy).", "{}");
            return resp.toString();
        }
    }

    private String login(HttpServletRequest request, Conexion con) {
        try {

            String usuario = request.getParameter("usuario");
            String pass = request.getParameter("pass");
            USUARIO usr = new USUARIO(con);
            JSONObject obj = usr.getByUsrPass(usuario, pass);
            RESPUESTA resp = new RESPUESTA(0, "", "No se encontro el usuario.", obj.toString());
            if (obj.getString("exito").equals("si")) {
                resp.setEstado(1);
                resp.setResp(obj.toString());
                resp.setMensaje("Login exitoso.");
            }
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al buscar el usuario.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }

    }
//</editor-fold>

}
