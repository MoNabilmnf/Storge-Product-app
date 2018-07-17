package com.example.mohamed_nabil.storgedatabc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to exit ? ");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ActivityCompat.finishAffinity(MainActivity.this);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void add(View view)
    {
        Intent intent = new Intent(MainActivity.this, BarCode.class);
        startActivity(intent);

    }
    public void delete(View view)
    {
        Intent intent = new Intent(MainActivity.this, DeletePro.class);
        startActivity(intent);

    }
    public void search(View view)
    {
        Intent intent = new Intent(MainActivity.this, ShowProduct.class);
        startActivity(intent);

    }
}
