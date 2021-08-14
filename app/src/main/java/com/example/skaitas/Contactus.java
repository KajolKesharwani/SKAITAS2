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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Contactus extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText fnametxt;
    private EditText lnametxt;
    private EditText emailidtxt;
    private EditText phno;
    private EditText msgtxt;
    private Button sendbtn;
    private String spinner_text;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getNavigationIcon().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Contactus.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Subjects));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(this);

        fnametxt = findViewById(R.id.fnametxt);
        lnametxt = findViewById(R.id.lnametxt);
        emailidtxt = findViewById(R.id.emailidtxt);
        phno = findViewById(R.id.phnno);
        msgtxt = findViewById(R.id.msgtxt);
        sendbtn = findViewById(R.id.sendbtn);

         sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FirstName = fnametxt.getText().toString();
                String LastName = lnametxt.getText().toString();
                String EmailId = emailidtxt.getText().toString();
                String MobileNumber = phno.getText().toString();
                String Message = msgtxt.getText().toString();
                String Send = sendbtn.getText().toString();
                String subject = spinner_text;
                // Toast.makeText(Contact.this, spinner_text, Toast.LENGTH_SHORT).show();

                boolean check = validateinfo(FirstName, LastName, EmailId, MobileNumber, Message, Send);

                if (check == true) {

                    Map<String, Object> v = new HashMap<>();
                    v.put("name", FirstName + " " + LastName);
                    v.put("email", EmailId);
                    v.put("Mobile_no", MobileNumber);
                    v.put("Subject", subject);
                    v.put("Message", Message);
                    FirebaseFirestore.getInstance().collection("Contact").add(v).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(getApplicationContext(), "INSERTED", Toast.LENGTH_SHORT).show();

                        }
                    });



                }
            }
        });
    }


        private boolean validateinfo (String firstName, String lastName, String emailId, String
        mobileNumber, String message, String send)
        {
            if (firstName.length() == 0) {
                fnametxt.requestFocus();
                fnametxt.setError("FIELD CANNOT BE EMPTY");
                return false;
            } else if (!firstName.matches("[a-zA-Z]+")) {
                fnametxt.requestFocus();
                fnametxt.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                return false;
            } else if (lastName.length() == 0) {
                    lnametxt.requestFocus();
                    lnametxt.setError("FIELD CANNOT BE EMPTY");
                    return false;
            } else if (!lastName.matches("[a-zA-Z]+")) {
                    lnametxt.requestFocus();
                    lnametxt.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                    return false;
            } else if (emailId.length() == 0) {
                emailidtxt.requestFocus();
                emailidtxt.setError("FIELD CANNOT BE EMPTY");
                return false;
            } else if (!emailId.matches("[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                emailidtxt.requestFocus();
                emailidtxt.setError("ENTER VALID EMAIL");
                return false;
            } else if (mobileNumber.length() == 0) {
                phno.requestFocus();
                phno.setError("FIELD CANNOT BE EMPTY");
                return false;
            } else if (!mobileNumber.matches("^[+][0-9]{10,13}$")) {
                phno.requestFocus();
                phno.setError("correct Format: +91xxxxxxxxxx");
                return false;
            } else if (message.isEmpty()) {
                msgtxt.requestFocus();
                msgtxt.setError("Message Cannot Be Empty");
                return false;
            } else {
                return true;
            }
        }


        @Override
        public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){
            spinner_text = adapterView.getItemAtPosition(i).toString();
        }

        @Override
        public void onNothingSelected (AdapterView < ? > adapterView){

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







