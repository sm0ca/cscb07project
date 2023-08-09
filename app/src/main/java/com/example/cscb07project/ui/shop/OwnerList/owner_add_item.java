package com.example.cscb07project.ui.shop.OwnerList;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cscb07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class owner_add_item extends Fragment {

    ImageView imageViewProduct;
    EditText itemName;
    EditText itemBrand;
    EditText itemDescription;
    EditText itemPrice;
    Uri imageUri = null;
    StorageReference storageReference;
    MaterialButton uploadButton;
    String storeName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    public owner_add_item() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewProduct = view.findViewById(R.id.isOwner_item_logo);
        uploadButton = view.findViewById(R.id.isOwner_add_button);
        itemName = view.findViewById(R.id.item_name);
        itemBrand = view.findViewById(R.id.item_brand);
        itemDescription = view.findViewById(R.id.item_description);
        itemPrice = view.findViewById(R.id.item_price);


        db.getReference("users/" + user.getUid() + "/storeName").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()) {
                            storeName = String.valueOf(task.getResult().getValue());
                        }
                    }
                });

        imageViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageReference = FirebaseStorage.getInstance().getReference("images/" + storeName +
                        "/" + String.valueOf(itemName.getText()));

                if(emptyCheck()) {
                    uploadDetails();
                    uploadImage();

                }

            }
        });


    }

    private void uploadDetails() {
        DatabaseReference dbDetailsReference = FirebaseDatabase.getInstance().getReference("stores/"
                + storeName + "/" + "items");

        // setValue itemBrand
        dbDetailsReference.child(String.valueOf(itemName.getText()))
                .child("brand").setValue(String.valueOf(itemBrand.getText()));

        // setValue itemDescription
        dbDetailsReference.child(String.valueOf(itemName.getText()))
                .child("description").setValue(String.valueOf(itemDescription.getText()));

        // setValue forSale to true
        dbDetailsReference.child(String.valueOf(itemName.getText()))
                .child("forSale").setValue(true);

        // setValue image to the image's path in cloud storage
        dbDetailsReference.child(String.valueOf(itemName.getText()))
                .child("image").setValue(storageReference.getPath());

        // setValue to price
        dbDetailsReference.child(String.valueOf(itemName.getText()))
                .child("price").setValue(Float.valueOf(String.valueOf(itemPrice.getText())));

    }

    private void uploadImage() {

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), getString(R.string.successfully_uploaded_item), Toast.LENGTH_SHORT).show();

                        // go back to other fragment
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.action_owner_list_add_to_owner_list);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), getString(R.string.failed_to_upload_item), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 69);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 69 && resultCode == RESULT_OK && data.getData() != null) { //nice!

            imageUri = data.getData();
            imageViewProduct.setImageURI(imageUri);
        }
    }

    private boolean emptyCheck() {
        if(TextUtils.isEmpty(String.valueOf(itemName.getText()))) {
            Toast.makeText(getActivity(), "Enter item name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(String.valueOf(itemBrand.getText()))) {
            Toast.makeText(getActivity(), "Enter item brand", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(String.valueOf(itemDescription.getText()))) {
            Toast.makeText(getActivity(), "Enter item description", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(String.valueOf(itemPrice.getText()))) {
            Toast.makeText(getActivity(), "Enter item price", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(imageUri == null) {
            Toast.makeText(getActivity(), "Enter item picture", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}