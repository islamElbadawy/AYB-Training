package com.example.ayb_itshare;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_Activity extends AppCompatActivity {

    private EditText userName;
    private EditText email;
    private EditText password;
    private EditText password2;
    private Button submit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        userName = findViewById(R.id.Reg_name);
        email = findViewById(R.id.Reg_mail);
        password = findViewById(R.id.Reg_password);
        password2 = findViewById(R.id.Reg_password2);
        submit = findViewById(R.id.Reg_submit);
        mAuth = FirebaseAuth.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp(v);

            }
        });



    }

    private void  signUp (View view){

        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Register_Activity.this,"success",Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(Register_Activity.this,"failed" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });


    }


}
