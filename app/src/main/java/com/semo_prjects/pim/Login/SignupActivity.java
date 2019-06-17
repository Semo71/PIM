package com.semo_prjects.pim.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.semo_prjects.pim.MainActivity;
import com.semo_prjects.pim.R;

public class SignupActivity extends AppCompatActivity {
    //Declaring Variables
    private EditText mEmailTextP2;
    private EditText mPasswordTextP2;
    private EditText mConfirmEmailTextP2;
    private Button mSigniupButtonP2;
    private Button mLoginButtonP2;
    private ProgressBar mSignupProgressP2;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Initializing the variables
        mEmailTextP2 = (EditText)findViewById(R.id.emailText);
        mPasswordTextP2 = (EditText)findViewById(R.id.passwordText);
        mConfirmEmailTextP2 = (EditText)findViewById(R.id.confirmPasswordText);
        mSigniupButtonP2 = (Button)findViewById(R.id.signupButton);
        mLoginButtonP2 = (Button)findViewById(R.id.loginButton);
        mSignupProgressP2 = (ProgressBar)findViewById(R.id.signupProgress);
        mAuth = FirebaseAuth.getInstance();

        //Activating the Login button
        mLoginButtonP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToLogin();
            }
        });

        //Activating the Signup button
        mSigniupButtonP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extracting the pure text
                String email = mEmailTextP2.getText().toString();
                String password = mPasswordTextP2.getText().toString();
                String confirmPassword = mConfirmEmailTextP2.getText().toString();
                //checking if the extracted email and password are not empty
                // then make the progress bar visible
                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)&!TextUtils.isEmpty(confirmPassword)){
                    if(password.equals(confirmPassword)){
                        mSignupProgressP2.setVisibility(View.VISIBLE);
                        //creating email and password data in firebase then checking success
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent setupIntent = new Intent(SignupActivity.this,SetupActivity.class);
                                    startActivity(setupIntent);
                                    finish();

                                }else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(SignupActivity.this,"Error is "+error,Toast.LENGTH_LONG).show();

                                }
                                //invisible the progress bar
                                mSignupProgressP2.setVisibility(View.INVISIBLE);

                            }
                        });

                    }else {
                        Toast.makeText(SignupActivity.this,"Password and Confirm Password doesn't match",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            sendToMain();

        }

    }
    private void sendToMain() {

        Intent mainIntent = new Intent(SignupActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

}

