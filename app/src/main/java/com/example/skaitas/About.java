package com.example.skaitas;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class About extends AppCompatActivity {

    Button goalbtn,publicationbtn,productbtn;
    TextView goaltxt,publicationtxt,producttxt;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.getNavigationIcon().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        goalbtn = findViewById(R.id.goalbutton);
        goaltxt = findViewById(R.id.goalstxt);
        publicationbtn = findViewById(R.id.publicationbutton);
        publicationtxt = findViewById(R.id.publicationtxt);
        productbtn = findViewById(R.id.productbutton);
        producttxt = findViewById(R.id.producttxt);

        goalbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(goaltxt.getVisibility()==View.GONE){
                    publicationtxt.setVisibility(View.GONE);
                    producttxt.setVisibility(View.GONE);
                    goaltxt.setVisibility(View.VISIBLE);
                }else{
                    goaltxt.setVisibility(View.GONE);

                }
            }
        });

        publicationbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v1) {
                if(publicationtxt.getVisibility()==View.GONE){
                    goaltxt.setVisibility(View.GONE);
                    producttxt.setVisibility(View.GONE);
                    publicationtxt.setVisibility(View.VISIBLE);
                }else{
                    publicationtxt.setVisibility(View.GONE);

                }
            }
        });

        productbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v2) {
                if(producttxt.getVisibility()==View.GONE){
                    goaltxt.setVisibility(View.GONE);
                    publicationtxt.setVisibility(View.GONE);
                    producttxt.setVisibility(View.VISIBLE);
                }else{
                    producttxt.setVisibility(View.GONE);

                }
            }
        });

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