package com.example.android.tflitecamerademo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class CaptureActivity extends Activity {
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    Button btnCamera,logout,recentimages;
    TextView name;
    String pathToFile;
    private static final int CAMERA_REQUEST = 1888;
    String mCurrentPhotoPath;
    String namefile;

    private File createImageFile() throws IOException {
// Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new     Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

// Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    Uri photoURI;
    Bitmap bitmap;
    String use;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
         btnCamera = findViewById(R.id.opencamera);
         name=findViewById(R.id.name);
         logout=findViewById(R.id.logout);




        if(Build.VERSION.SDK_INT>=23){
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }
        btnCamera.setOnClickListener(view ->
            dispatchPictureTakerAction()
        );

        Intent intent = getIntent();
         use = intent.getStringExtra("user");
        mStorageRef = FirebaseStorage.getInstance().getReference(use);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(use);

        String user="Hello "+use+",";
        name.setText(user);
       logout.setOnClickListener(v -> {
           FirebaseAuth.getInstance().signOut();
           finish();
           startActivity(new Intent(CaptureActivity.this,LogInActivity.class));
       });

       recentimages=findViewById(R.id.recentimages);
        recentimages.setOnClickListener(v -> {
            openImagesActivity();
        });

    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, ImagesActivity.class);
        intent.putExtra("user",use);
        startActivity(intent);
    }

    private void dispatchPictureTakerAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;

        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Uri uri = data.getData();
                Bitmap photo =  data.getParcelableExtra("data");
                bitmap = Bitmap.createScaledBitmap(photo, 224, 224, false);
                //Image Storage
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] databaos = baos.toByteArray();


            namefile="Photo"+ new Date().getTime();
            StorageReference imagesRef = mStorageRef.child(namefile+".jpg");


            imagesRef.putBytes(databaos).continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imagesRef.getDownloadUrl();
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CaptureActivity.this,"Uploading finished...",Toast.LENGTH_LONG).show();
                        Upload upload=new Upload(namefile,task.getResult().toString());
                        String uploadId=mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(upload);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
            //imagesRef.putBytes(databaos).addOnSuccessListener(taskSnapshot ->{ Toast.makeText(CaptureActivity.this,"Uploading finished...",Toast.LENGTH_LONG).show();
            //Upload upload=new Upload(namefile,taskSnapshot.getMetadata().toString());
           // String uploadid=mDatabaseRef.push().getKey();
           // mDatabaseRef.child(uploadid).setValue(upload);
            //});
            //send this name to database
            //upload image


           //StorageReference filepath=mStorageRef.child("Photos").child(uri.getLastPathSegment());

                Intent intent=new Intent(this,BitmapFragment.class);
                intent.putExtra("bitmap", bitmap);

                startActivity(intent);

                //imageView.setImageBitmap(photo);
                // Bitmap bitmap= BitmapFactory.decodeFile(pathToFile);
                //imageView.setImageBitmap(bitmap);

                //}


        }
        //Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        //imageView.setImageBitmap(bitmap);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
