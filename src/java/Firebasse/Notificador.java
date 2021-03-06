/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firebasse;

//import com.google.gson.Gson;

import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.BasicResponseHandler;
//import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author josec
 */
public class Notificador {
    
    
    public static boolean enviar(DataToSend dato) throws Exception{
        
        
        String url = "https://fcm.googleapis.com/fcm/send";
        HttpPost httpPost = new HttpPost(url);

        
        //add reuqest header
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "key=AAAA--Kl6Fk:APA91bHDLSvJdZ5ntFRuuzK2AMLvRHLoYmvmHxzRv-xoN50wHeBsVOISNMiPrMCVcUMaPOhWi7Gcc-nfpxZFW6TdWIzTtgF38vPjUpnL_x7SzSFTZ0cEy5SPP_vA7_b3l-EuE9KRqoyv");
        
        
        Gson gson = new Gson();
        String params = gson.toJson(dato);
        
        System.out.println("Sending...");
        System.out.println(params);
        System.out.println("");
        
        StringEntity entity = new StringEntity(params);

       // JSON-Object übergeben
        httpPost.setEntity(entity);

        HttpClient httpClient = HttpClientBuilder.create().build();

        BasicResponseHandler responseHandler = new BasicResponseHandler();
       
        String response = (String) httpClient.execute(httpPost, responseHandler);
        JSONObject obj = new JSONObject(response);
        if(obj.getInt("success")==1){
            return true;
        }else{
        return false;
                }

        
    }
    
}
