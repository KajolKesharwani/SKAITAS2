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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contactus extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText fname,lname,emailid,phnnotxt,messagetxt;
    Spinner subjecttxt;
    Button sendbutton;
    String item;
    DatabaseReference reff;
    Contactus1 contactus1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.getNavigationIcon().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Contactus.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Subjects));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        fname = findViewById(R.id.fnametxt);
        lname = findViewById(R.id.lnametxt);
        emailid = findViewById(R.id.emailidtxt);
        phnnotxt = findViewById(R.id.phnno);
        subjecttxt = findViewById(R.id.spinner1);
        messagetxt = findViewById(R.id.msgtxt);
        sendbutton = findViewById(R.id.sendbtn);
        subjecttxt.setOnItemSelectedListener(this);
        contactus1 = new Contactus1();
        reff = FirebaseDatabase.getInstance().getReference("Contactus1");
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long phn = Long.parseLong(phnnotxt.getText().toString().trim());
                contactus1.setFname(fname.getText().toString().trim());
                contactus1.setLname(lname.getText().toString().trim());
                contactus1.setEmailid(emailid.getText().toString().trim());
                contactus1.setMessage(messagetxt.getText().toString().trim());
                contactus1.setPhnno(phn);
                SaveValue(item);
                reff.push().setValue(contactus1);
                Toast.makeText(Contactus.this,"Feedback Sent Successfully",Toast.LENGTH_LONG).show();
            }


        });

        Intent Intent = new Intent(Contactus.this, MainActivity.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = (String) subjecttxt.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void SaveValue(String item) {

        if(item=="Select Subject"){
            Toast.makeText(Contactus.this,"Please Select a Subject..!",Toast.LENGTH_LONG).show();
        }
        else{
            contactus1.setSubject(item);
        }

    }
}