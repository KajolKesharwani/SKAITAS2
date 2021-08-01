package com.example.skaitas;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Services extends AppCompatActivity {
    CardView aircv,aidcv,wacv,macv,dacv,stcv,dccv,becv;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.nav_Services);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class) );
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_Services:
                        return true;
                    case R.id.nav_Team:
                        startActivity(new Intent(getApplicationContext(),Team.class) );
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_Career:

                        startActivity(new Intent(getApplicationContext(),Career.class) );
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        aircv = findViewById(R.id.aircardview);
        aidcv = findViewById(R.id.aidcardview);
        wacv = findViewById(R.id.wacardview);
        macv = findViewById(R.id.marcardview);
        dacv = findViewById(R.id.dacardview);
        stcv = findViewById(R.id.stcardview);
        dccv = findViewById(R.id.dccardview);
        becv = findViewById(R.id.becardview);

        aircv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Services.this, "Coming Soon...",Toast.LENGTH_SHORT).show();
            }
        });

        aidcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Services.this, "Coming Soon...",Toast.LENGTH_SHORT).show();
            }
        });

        wacv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Services.this, "Coming Soon...",Toast.LENGTH_SHORT).show();
            }
        });

        macv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Services.this, "Coming Soon...",Toast.LENGTH_SHORT).show();
            }
        });

        dacv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Services.this, "Coming Soon...",Toast.LENGTH_SHORT).show();
            }
        });

        stcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Services.this, "Coming Soon...",Toast.LENGTH_SHORT).show();
            }
        });

        dccv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotourl("https://digicardz.skaitas.com/");
            }
        });

        becv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Services.this, "Coming Soon...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void gotourl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.abt:
                Intent intent4 = new Intent(this, About.class);
                this.startActivity(intent4);
                return true;
            case R.id.contact:
                Intent intent1 = new Intent(this, Contactus.class);
                this.startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}