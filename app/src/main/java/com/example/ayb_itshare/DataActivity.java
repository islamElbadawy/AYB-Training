package com.example.ayb_itshare;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class DataActivity extends AppCompatActivity {

    private EditText message, sender;
    private Button send;
    private ImageView sender_photo;
    ProgressDialog progressDialog;
    Uri photoUri;

    FirebaseDatabase database ;
    DatabaseReference myRef;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        message = findViewById(R.id.message);
        sender = findViewById(R.id.sender);
        send = findViewById(R.id.send);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        sender_photo = findViewById(R.id.sender_photo);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insert(v);

            }
        });

        sender_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage(v);


            }
        });



    }

    private void uploadImage(View view) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 1 && resultCode == RESULT_OK){

            photoUri = data.getData();
            sender_photo.setImageURI(photoUri);

        }


    }


    public void insert(View view){

        final StorageReference riverRef = mStorageRef.child("Photos/" + System.currentTimeMillis() + "." + getImageExt(photoUri));
        progressDialog.show();

        riverRef.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                riverRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Map<String,String> posts = new HashMap<>();
                        posts.put("Post",message.getText().toString());
                        posts.put("User",sender.getText().toString());
                        posts.put("Photo",uri.toString());

                        myRef.push().setValue(posts);
                        Toast.makeText(DataActivity.this,"Success",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();


                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(DataActivity.this,"failed" ,Toast.LENGTH_SHORT).show();

            }
        });




    }

    public String getImageExt(Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
}
