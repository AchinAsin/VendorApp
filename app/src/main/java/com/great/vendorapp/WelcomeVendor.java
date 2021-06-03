package com.great.vendorapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;

public class WelcomeVendor extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    ImageView imageView1, imageView2, imageView3;
    Button button1, button2, button3, buttonUpload, buttonShow;
    EditText editTextCategory, editTextProductName, editTextPriceAmount, editTextGSTAmount, editTextDeliveryCharges, editTextOffer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    Uri filePath;
    ArrayList<Uri> ImagesList = new ArrayList<>();
    Bitmap bitmap;
    String value;
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    private int upload_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_vendor);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        button1 = findViewById(R.id.buttonUpload1);
        button2 = findViewById(R.id.buttonUpload2);
        button3 = findViewById(R.id.buttonUpload3);
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonShow = findViewById(R.id.buttonShow);

        editTextCategory = findViewById(R.id.EditText_Category);
        editTextProductName = findViewById(R.id.EditText_ProductName);
        editTextPriceAmount = findViewById(R.id.editText_PriceAmount);
        editTextGSTAmount = findViewById(R.id.editText_GstAmount);
        editTextDeliveryCharges = findViewById(R.id.editText_DeliveryCharges);
        editTextOffer = findViewById(R.id.editText_Offer);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeVendor.this, ShowDataActivity.class));
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadFile();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(WelcomeVendor.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Toast.makeText(WelcomeVendor.this, "Please Select 3 images", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                startActivityForResult(intent, PICK_IMAGE);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(WelcomeVendor.this, "Please Grant Permission for Uploading the Data", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        })
                        .check();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data.getClipData() != null) {
                int countClipData = data.getClipData().getItemCount();
                int currentImageSelect = 0;
                while (currentImageSelect < countClipData) {
                    filePath = data.getClipData().getItemAt(currentImageSelect).getUri();
                    ImagesList.add(filePath);
                    currentImageSelect = currentImageSelect + 1;
                }
            }
        }
    }

    private void UploadFile() {
        /*final String fileName = System.currentTimeMillis() + "";*/


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File Uploader");
        progressDialog.show();

        for (upload_count = 0; upload_count < ImagesList.size(); upload_count++) {
            Uri IndividualImage = ImagesList.get(upload_count);
            String mimeType=getContentResolver().getType(IndividualImage);
            Cursor returnCursor =
                    getContentResolver().query(IndividualImage, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String fileName=returnCursor.getString(nameIndex);

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            final StorageReference storageReference = firebaseStorage.getReference().child("Uploads").child(fileName);
            arrayList = new ArrayList<>();
            storageReference.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            arrayList.add(String.valueOf(uri));

                            if (arrayList.size() == ImagesList.size()) {
                                imagesLink(arrayList);

                                double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                progressDialog.setProgress((int) progress);
                                progressDialog.dismiss();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

    }

    private void imagesLink(ArrayList<String> arrayList) {

        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 0; i < arrayList.size(); i++) {
            hashMap.put("ImgLink" + i, arrayList.get(i));
        }
        /*final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Uploads");*/
        StorageReference storageReference=FirebaseStorage.getInstance().getReference();

        String category = editTextCategory.getText().toString();
        String productName = editTextProductName.getText().toString();
        String priceAmount = editTextPriceAmount.getText().toString();
        String gST = editTextGSTAmount.getText().toString();
        String deliveryCharges = editTextDeliveryCharges.getText().toString();
        String offer = editTextOffer.getText().toString();

        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("Category", category);
        hashMap1.put("ProductName", productName);
        hashMap1.put("PriceAmount", priceAmount);
        hashMap1.put("GST", gST);
        hashMap1.put("DeliveryCharges", deliveryCharges);
        hashMap1.put("Offer", offer);
        hashMap1.putAll(hashMap);
        /*hashMap.put("imageUrl", pictureUrl);*/
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Uploads");
        databaseReference.push().setValue(hashMap1);

        /*databaseReference.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(WelcomeVendor.this, "Database Storing Done", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WelcomeVendor.this, "Failed Database Storing", Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}
