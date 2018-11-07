/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firebasse;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author josec
 */
public class DataToSend {
    
    private Hashtable<String, String> data;
    private Hashtable<String, String> notification;
    private String priority;
    
    private List<String> registration_ids;

    public DataToSend() {
        data = new Hashtable<>();
        notification = new Hashtable<>();
        registration_ids = new LinkedList<>();
        priority = "high";
        registration_ids.add("");
    }
    
    public DataToSend(String token, String evento,String json, String id) {
        data = new Hashtable<>();
        data.put("evento", evento);
        data.put("json", json);
        data.put("id", id);
        notification = new Hashtable<>();
        registration_ids = new LinkedList<>();
        priority = "high";
        registration_ids.add(token);
    }
    public DataToSend(String token, String evento,String json,String jsonusuario, String id) {
        data = new Hashtable<>();
        data.put("evento", evento);
        data.put("json", json);
        data.put("jsonUsuario", jsonusuario);
        data.put("id", id);
        notification = new Hashtable<>();
        registration_ids = new LinkedList<>();
        priority = "high";
        registration_ids.add(token);
    }
}
