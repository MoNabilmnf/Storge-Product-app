package com.example.mohamed_nabil.storgedatabc;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.core.CameraPreview;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ShowProduct extends AppCompatActivity {
    private ZXingScannerView scannerView;
    String rcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
    }

    public void br(View view)
    {
        scannerView = new ZXingScannerView(ShowProduct.this);
        scannerView.setResultHandler(new ZX() );

        setContentView(scannerView);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scannerView != null)
          scannerView.stopCamera();
        else
            finish();
       }


    class ZX implements ZXingScannerView.ResultHandler {


        @Override
        public void handleResult(Result result) {
            rcode = result.getText();


            setContentView(R.layout.activity_bar_code);
            scannerView.stopCamera();
            RequestQueue requestQueue = Volley.newRequestQueue(ShowProduct.this);
            String url_like = "http://192.168.1.10/checkbr.php?barcode=" + rcode;
            StringRequest request = new StringRequest(Request.Method.GET, url_like, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            Toast.makeText(ShowProduct.this, "This BarCode does not exists", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ShowProduct.this, MainActivity.class);

                            startActivity(intent);

                        } else {

                            Intent intent = new Intent(ShowProduct.this, ShowProduct2.class);
                            intent.putExtra("br", rcode);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", "ERROR");
                }
            }
            );
            requestQueue.add(request);
        }
    }

}
