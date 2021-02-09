package com.example.pickit.shopowner;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.pickit.R;
import com.example.pickit.shopowner.model.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProActivity extends AppCompatActivity {
    public static final int CAMERA = 102;
    public static final int REQUEST_CODE = 101;
    public static final int CODE = 105;
    public static final int GALLERY_CODE = 100;
    TextInputEditText proname,proprice;
    ImageView imageView;
    private Uri uri;
    Uri photoURI;
    private String name,price,downUrl;
    private StorageReference storageReference;
    String UUID= ShopOwnerHomeScreen.UUID;
    Button camera,gallery;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pro);
        storageReference= FirebaseStorage.getInstance().getReference().child("Product Images");
        proname=findViewById(R.id.pname);
        proprice=findViewById(R.id.pprice);
        imageView=findViewById(R.id.pimage);
        //camera=findViewById(R.id.camera);
        gallery=findViewById(R.id.gallery);


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askGalleryPer();
            }
        });

       /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });*/
    }

    private void askGalleryPer() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_CODE);
        }
        else {
            OpenGallery();
        }
    }


    private void askCameraPer() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
        }
        else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }
            else {
                Toast.makeText(this, "Camera permission is Required to use Camera", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==GALLERY_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                OpenGallery();
            }
            else {
                Toast.makeText(this, "Storage permission is Required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Toast.makeText(this, "Camera Open Request", Toast.LENGTH_SHORT).show();
        Intent cam= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cam,CAMERA);
    }

    private void OpenGallery() {
        Intent gallery=new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery,CODE);
    }

    String mCurrentPhotoPath;

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
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            imageView.setImageURI(uri);
        }
        /*if(requestCode==CAMERA && resultCode==RESULT_OK && data!=null){
            uri = data.getData();

            StorageReference filepath =     storageReference.child("Photos").child(UUID);
            filepath.putFile(photoURI).addOnSuccessListener(new    OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddProActivity.this, "Upload Successful!",    Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddProActivity.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }
            /*StorageReference filepath=storageReference.child("Product Images/").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(AddProActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddProActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            });*/

    }

    private void uploadImg(String name, Uri contentUri) {
        storageReference.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(AddProActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    public void addPro(View view) {
        ValidateData();
    }

    private void ValidateData() {
        name=proname.getText().toString().trim();
        price=proprice.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(AddProActivity.this,"Please give the Product Name ...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(price)){
            Toast.makeText(AddProActivity.this,"Please give the Product Price ...",Toast.LENGTH_SHORT).show();
        }
        else{
            StoreProduct();
        }
    }

    private void StoreProduct() {
        final StorageReference path=storageReference.child(uri.getLastPathSegment()+UUID);
        final UploadTask uploadTask=path.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(AddProActivity.this,"Error: "+message,Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddProActivity.this, "Product Image uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downUrl=path.getDownloadUrl().toString();
                        return path.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downUrl=task.getResult().toString();
                            Toast.makeText(AddProActivity.this, "Product Image Saved ...", Toast.LENGTH_SHORT).show();
                            SaveProduct();
                        }
                    }
                });
            }
        });
    }

    private void SaveProduct() {
        Product product;
        double value= Double.valueOf(price);
        String availability="true";
        String UUID= ShopOwnerHomeScreen.UUID;
        product=new Product(UUID,name,value,availability,downUrl);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShopOwner/"+UUID+"/ProductDetails");
        databaseReference.child(name).setValue(product);
    }
}