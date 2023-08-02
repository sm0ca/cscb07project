package com.example.cscb07project.ui.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class activity_register extends AppCompatActivity {

//    TextInputEditText editTextUsername;       MIGHT BE REMOVED
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextStoreName;
    private Button button;
    private TextView redirect;
    private FirebaseAuth mAuth;
    private static ProgressBar registerProgressBar;
    private RadioGroup isOwner_option;
    private int isOwner_check;
    private createUser user;

    // get functions
    public static ProgressBar getRegisterProgressBar() {
        return registerProgressBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();


        // Initialize
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextStoreName = findViewById(R.id.store_name);
        button = findViewById(R.id.register_button);
        registerProgressBar = findViewById(R.id.progress_bar);
        redirect = findViewById(R.id.redirect);

        // create using email button's onclick listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;
                String password;
                String storeName;

                // Progress bar visibility
                registerProgressBar.setVisibility(View.VISIBLE);

                // getting the String from the editText username and password
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                storeName = String.valueOf(editTextStoreName.getText());

                // Check if email and password is empty
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(activity_register.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                    registerProgressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(activity_register.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                    registerProgressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(isOwner_check == R.id.radioButton_register_owner && TextUtils.isEmpty(storeName)) {
                    Toast.makeText(activity_register.this, "Enter your store name", Toast.LENGTH_SHORT).show();
                    registerProgressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                // Creating user
                user = new createUserEmail(email, password, storeName, isOwner_check, mAuth, activity_register.this, view);
                user.create();
                return;
            }
        });





        // redirect to login when clicking login
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_login.class);
                startActivity(intent);
                finish();
            }
        });

        // Checking if logged in
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // check if owner or customer
        isOwner_option = findViewById(R.id.radioButton_register);
        isOwner_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isOwner_check = i;

                // change storeName visibility
                if(isOwner_check == R.id.radioButton_register_owner) {
                    editTextStoreName.setVisibility(View.VISIBLE);
                }
                else {
                    editTextStoreName.setVisibility(View.GONE);
                }
            }
        });
    }
}