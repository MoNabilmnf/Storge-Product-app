package com.example.mohamed_nabil.storgedatabc;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowProduct2 extends AppCompatActivity {
RequestQueue requestQueue;
    TextView name,price;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product2);
        name = (TextView)findViewById(R.id.textView4);
        price = (TextView)findViewById(R.id.textView5);
        image = (ImageView)findViewById(R.id.imageView4);
        Intent date = getIntent();
        final String bc = date.getExtras().getString("br");
        String url = "http://192.168.1.10/showpro.php?barcode="+bc;

        requestQueue = Volley.newRequestQueue(ShowProduct2.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject res = jsonArray.getJSONObject(i);
                        String Name = res.getString("name");
                        String Price = res.getString("price");
                        String Img = res.getString("img");

                        name.append("product name : " + Name);
                        price.append("product price : " + Price + " LE");
                        Picasso.with(getApplicationContext()).load("http://192.168.1.10/image/" + Img).into(image);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY","ERROR");
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

}
