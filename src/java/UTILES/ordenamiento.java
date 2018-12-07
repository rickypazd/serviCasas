/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UTILES;

/**
 *
 * @author ricardopazdemiquel
 */
import org.json.JSONObject;

public class ordenamiento {

    private int dist;
    private JSONObject obj;

    public ordenamiento(int dist, JSONObject obj) {
        this.dist = dist;
        this.obj = obj;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public JSONObject getObj() {
        return obj;
    }

    public void setObj(JSONObject obj) {
        this.obj = obj;
    }

    public static ordenamiento[] OrdenarBurbuja(ordenamiento[] n) {
        ordenamiento temp;
        int t = n.length;
        for (int i = 1; i < t; i++) {
            for (int k = t - 1; k >= i; k--) {
                if (n[k].getDist() < n[k - 1].getDist()) {
                    temp = n[k];
                    n[k] = n[k - 1];
                    n[k - 1] = temp;
                }
            }
        }
        return n;
    }
}
