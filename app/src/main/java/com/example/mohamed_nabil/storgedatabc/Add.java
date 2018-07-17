package com.example.mohamed_nabil.storgedatabc;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Add extends AppCompatActivity {
    String code, rcode;
    ImageView imgV;
    EditText name,price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        imgV = (ImageView) findViewById(R.id.imageView);
        name = (EditText)findViewById(R.id.name_edit);
        price = (EditText)findViewById(R.id.price_edit);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Add.this, MainActivity.class);
        startActivity(intent);
    }


    public void img(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {

            imgV.setImageBitmap((Bitmap) data.getExtras().get("data"));

        }
    }


    public void send(View view) {
        Bitmap bitmap = ((BitmapDrawable) imgV.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        code = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        String Name = name.getText().toString();
        String Price = price.getText().toString();
        Intent date = getIntent();
        String bc = date.getExtras().getString("br");

        Response.Listener<String> responselisner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonRespons = new JSONObject(response);
                    boolean success = jsonRespons.getBoolean("success");
                    if (success) {
                        Toast.makeText(Add.this, "Send Successfuly", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Add.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Add.this, "does not Send", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Send_Data send_data = new Send_Data(code, bc,Name,Price, responselisner);
        RequestQueue queue = Volley.newRequestQueue(Add.this);
        queue.add(send_data);


    }
}




