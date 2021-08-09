package com.example.skaitas;

import androidx.annotation.NonNull;
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

    private static final int PICK_FILE_REQUEST =234 ;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText phnno;
    private Button bt_apply;
    private Button bt_file;
    private Button bt_Uploadresume;
    private String spinner_text;


    private Uri filePath;

    private StorageReference storageReference;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_javainternship);

        storageReference= FirebaseStorage.getInstance().getReference();

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
        bt_file = (Button) findViewById(R.id.bt_file);
        bt_Uploadresume = (Button) findViewById(R.id.bt_uploadresume);




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

    private void showFileChooser(){
        Intent intent =  new Intent();
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Images"), PICK_FILE_REQUEST);
    }

    private void Uploadresume() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("FILE/pic.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot tasksnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * tasksnapshot.getBytesTransferred()) / tasksnapshot.getTotalByteCount();



                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }

            //if there is not any file
        else{
                //you can display an error toast
            }
        }
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
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
