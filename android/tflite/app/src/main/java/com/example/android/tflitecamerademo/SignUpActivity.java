package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;


public class SignUpActivity extends Activity  implements View.OnClickListener{
    private Button btnLogin,btnSignup,button3;
    EditText editText,editText2,editText3;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(SignUpActivity.this);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnSignup=(Button)findViewById(R.id.btnSignup);
        editText=findViewById(R.id.editText);
        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);


        findViewById(R.id.button3).setOnClickListener(this);

        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this,LogInActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onClick(View v) {
    switch(v.getId()){
        case R.id.button3:
            registerUser();
            break;
       // case R.id.btnLogin:
         //   startActivity(new Intent(this,CaptureActivity.class));
        //    break;
    }
    }

    private void registerUser() {
        String username=editText.getText().toString().trim();
        String password=editText2.getText().toString().trim();
        String confirmp=editText3.getText().toString().trim();

        if(username.isEmpty()){
            editText.setError("Email is required");
            editText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            editText.setError("Please enter a valid email address");
            editText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editText2.setError("Password is required");
            editText2.requestFocus();
            return;
        }
        if(password.length()<=6){
            editText2.setError("Minimum length of password should be 6");
            editText2.requestFocus();
            return;

        }
        if(!password.equals(confirmp)){
            editText3.setError("Passwords doesnt match");
            editText3.requestFocus();
            return;

        }
        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(task -> {
            try {
                //check if successful
                if (task.isSuccessful()) {
                    //User is successfully registered and logged in
                    //start Profile Activity here
                    Toast.makeText(SignUpActivity.this, "Registration successful",
                            Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent=new Intent(SignUpActivity.this,CaptureActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    String u[]=username.split("@",2);
                    intent.putExtra("user", u[0]);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignUpActivity.this, "Couldn't register, try again",
                            Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

}
