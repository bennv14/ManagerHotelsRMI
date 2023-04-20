/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIClient;

import java.util.List;
import model.Hotel;

/**
 *
 * @author ben
 */
public class Test {
    public static void main(String[] args) {
        String type = "tất cả";
        String price = "";
        String strSearch="";
        if (type != "tất cả" && !price.isBlank()) {
            strSearch += "Where loaiphong = '" + type + "' AND giaphong <= " + price ;
        }
        else if (type != "tất cả"){
            strSearch += "Where loaiphong = '" + type + "'";
        }
        else if(!price.isBlank()){
            strSearch += "Where giaphong <= " + price ;
        }
        String sql = "SELECT * FROM phong " + strSearch;
        System.out.println(sql);
    }
    
    
    
}
