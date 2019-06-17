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

public class LoginActivity extends AppCompatActivity {

   //Declaring Variables
    private EditText mEmailText;
    private EditText mPasswordText;
    private Button mLoginButton;
    private Button mSigniupButton;
    private ProgressBar mLoginProgress;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initializing the variables
        mEmailText = (EditText) findViewById(R.id.emailText);
        mPasswordText = (EditText) findViewById(R.id.passwordText);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mSigniupButton = (Button) findViewById(R.id.signupButton);
        mLoginProgress = (ProgressBar) findViewById(R.id.loginProgress);
        mAuth = FirebaseAuth.getInstance();

        //Activating the Signup button
        mSigniupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToSignup();
            }
        });


        //Activating the Login button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extracting the pure text
                String email = mEmailText.getText().toString();
                String password = mPasswordText.getText().toString();
                //checking if the extracted email and password are not empty
                // then make the progress bar visible
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    mLoginProgress.setVisibility(View.VISIBLE);
                    //comparing the inputs with the data in firebase then checking correctness

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                sendToMain();
                            }else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                            }

                            mLoginProgress.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            }
        });
    }



    //Checking the user state whenever app starts and send it to main page
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            sendToMain();
        }
    }


    //to send user to the MainActivity
    private void sendToMain() {

        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    //to send user to the SignupActivity
    private void sendToSignup() {

        Intent signupIntent = new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(signupIntent);
        finish();
    }
}
