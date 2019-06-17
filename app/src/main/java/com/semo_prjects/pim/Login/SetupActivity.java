package com.semo_prjects.pim.Login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.semo_prjects.pim.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class SetupActivity extends AppCompatActivity {

    private ProgressBar mSetupProgress;
    private CircleImageView mSetupImage;
    private Uri mainImageURI=null;
    private EditText mSetupName;
    private Button mSetupButton;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mSetupProgress =findViewById(R.id.setupProgress);
        mSetupImage = findViewById(R.id.setupImage);
        mSetupName = findViewById(R.id.setupName);
        mSetupButton = findViewById(R.id.setupButton);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        Toolbar mSetupToolbar = findViewById(R.id.setupToolbar);
        setSupportActionBar(mSetupToolbar);
        getSupportActionBar().setTitle("Account Setup");

        mSetupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(SetupActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            !=PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(SetupActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(SetupActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else{
                        bringImagePickerr();

                    }

                }else{
                    bringImagePickerr();

                }

            }
        });

        mSetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mSetupName.getText().toString();
                if(!TextUtils.isEmpty(userName)&& mainImageURI!=null){
                    String userID = mAuth.getCurrentUser().getUid();
                    mSetupProgress.setVisibility(View.VISIBLE);
                    StorageReference imagePath = mStorageRef.child("profile_images").child(userID+".jpg");
                    imagePath.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       if (task.isSuccessful()){
                            Toast.makeText(SetupActivity.this,"Image is uploaded But still missing code",
                                   Toast.LENGTH_LONG).show();



                       }else {
                           String error = task.getException().getMessage();
                           Toast.makeText(SetupActivity.this,"Error"+error,
                                   Toast.LENGTH_LONG).show();

                       }
                            mSetupProgress.setVisibility(View.INVISIBLE);




                        }
                    });


                }
            }
        });
    }

    private void bringImagePickerr() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(SetupActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainImageURI = result.getUri();
                mSetupImage.setImageURI(mainImageURI);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
