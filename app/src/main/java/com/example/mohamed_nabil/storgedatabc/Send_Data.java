package com.example.mohamed_nabil.storgedatabc;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nody on 22/12/2017.
 */
public class Send_Data extends StringRequest {
    private static final String url = "http://192.168.1.10/add_image.php";
    private Map<String , String> MapData;

    public Send_Data(String img,String barcode,String name,String price, Response.Listener<String> listener)
    {
        super(Method.POST,url,listener,null);
        MapData = new HashMap<>();
        MapData.put("img",img);
        MapData.put("barcode",barcode);
        MapData.put("name",name);
        MapData.put("price",price);
    }

    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}
