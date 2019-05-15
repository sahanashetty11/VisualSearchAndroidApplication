package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;


public class LogInActivity extends Activity implements View.OnClickListener {
    private Button btnLogin,btnSignup;
    private FirebaseAuth mAuth;
    EditText editText,editText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editText=findViewById(R.id.editText);
        editText2=findViewById(R.id.editText2);


        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnSignup=(Button)findViewById(R.id.btnSignup);
        findViewById(R.id.button3).setOnClickListener(this);

        btnSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LogInActivity.this,SignUpActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button3:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String username=editText.getText().toString().trim();
        String password=editText2.getText().toString().trim();

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
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //User is successfully registered and logged in
                //start Profile Activity here
                Intent intent=new Intent(LogInActivity.this,CaptureActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String u[]=username.split("@",2);
                intent.putExtra("user", u[0]);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(LogInActivity.this, "Couldn't login, try again",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
