package com.example.skaitas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class javainternship extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText phnno;
    private Button bt_apply;
    private EditText ed_file;
    private Button bt_Uploadresume;
    private String spinner_text;


    private Uri filePath;

    private StorageReference storageReference;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_javainternship);

        storageReference= FirebaseStorage.getInstance().getReference("uploadPDF");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getNavigationIcon().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);
        Spinner mySpinner1 = (Spinner) findViewById(R.id.spinner3);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(javainternship.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Subject1));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(javainternship.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Subject2));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(this);
        mySpinner1.setAdapter(myAdapter1);
        mySpinner1.setOnItemSelectedListener(this);




        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phnno = findViewById(R.id.phnno);
        ed_file = findViewById(R.id.ed_file);
        bt_Uploadresume = (Button) findViewById(R.id.bt_uploadresume);

        bt_Uploadresume.setEnabled(false);

        ed_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });





        bt_apply = (Button) findViewById(R.id.bt_apply);

        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FirstName = firstname.getText().toString();
                String LastName = lastname.getText().toString();
                String EmailId = email.getText().toString();
                String MobileNumber = phnno.getText().toString();
                String apply = bt_apply.getText().toString();
                String subject1 = spinner_text;
                String subject2 =spinner_text;



                boolean check = validateinfo(FirstName, LastName, EmailId, MobileNumber, subject1 ,apply);

                if (check == true) {

                    Map<String, Object> v = new HashMap<>();
                    v.put("firstname", FirstName + " " + LastName);
                    v.put("email", EmailId);
                    v.put("phnno", MobileNumber);
                    v.put("Subject", subject1);
                    v.put("Subject2", subject2);

                    FirebaseFirestore.getInstance().collection("Internship Information").add(v).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(getApplicationContext(), "INSERTED", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        });
    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);
    }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK && data.getData()!=null) {
            bt_Uploadresume.setEnabled(true);
            ed_file.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));

            bt_Uploadresume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPDFFileFirebase(data.getData());
                }
            });

        }
   }

    private void uploadPDFFileFirebase(Uri data) {
        final  ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("uploadPDF" + System.currentTimeMillis() + "pdf");

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        putPDF putPDF = new putPDF(ed_file.getText().toString(), uri.toString());
                        Toast.makeText(javainternship.this,"FILE UPLOADED", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploaded.." + (int) progress+ "%");

            }
        });





    }


    private boolean validateinfo(String firstName, String lastName, String emailId, String
            mobileNumber, String message, String apply) {
        if (firstName.length() == 0) {
            firstname.requestFocus();
            firstname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!firstName.matches("[a-zA-Z]+")) {
            firstname.requestFocus();
            firstname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (lastName.length() == 0) {
            lastname.requestFocus();
            lastname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!lastName.matches("[a-zA-Z]+")) {
            lastname.requestFocus();
            lastname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else if (emailId.length() == 0) {
            email.requestFocus();
            email.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!emailId.matches("[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            email.requestFocus();
            email.setError("ENTER VALID EMAIL");
            return false;
        } else if (mobileNumber.length() == 0) {
            phnno.requestFocus();
            phnno.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!mobileNumber.matches("^[0-9]{10,13}$")) {
            phnno.requestFocus();
            phnno.setError("correct Format: 91xxxxxxxxxx");
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onItemSelected (AdapterView< ? > adapterView, View view, int i, long l){
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
