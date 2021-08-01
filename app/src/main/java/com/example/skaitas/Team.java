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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class Team extends AppCompatActivity {

    private Button Advisors;
    private Button Team;
    private Button Consultants;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.nav_Team);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class) );
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_Services:
                        startActivity(new Intent(getApplicationContext(),Services.class) );
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_Team:
                        return true;
                    case R.id.nav_Career:
                        startActivity(new Intent(getApplicationContext(),Career.class) );
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Advisors = (Button) findViewById(R.id.button1);
        Advisors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdvisors();
            }

        });

        Team = (Button) findViewById(R.id.button2);
        Team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTeam();
            }


        });

        Consultants = (Button) findViewById(R.id.button3);
        Consultants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConsultants();
            }


        });

    }
    public void openAdvisors() {
        Intent Intent = new Intent(this, Advisors.class);
        startActivity(Intent);
    }

    public void openTeam() {
        Intent Intent = new Intent(Team.this, Team2.class);
        startActivity(Intent);
    }

    public void openConsultants() {
        Intent Intent = new Intent(Team.this, Consultants.class);
        startActivity(Intent);
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
