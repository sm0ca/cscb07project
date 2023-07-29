package com.example.cscb07project.ui.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_register extends AppCompatActivity {

//    TextInputEditText editTextUsername;       MIGHT BE REMOVED
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private Button button;
    private TextView redirect;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    // checking if user is logged in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();


        // Initialize
//        editTextUsername = findViewById(R.id.username);       MIGHT BE REMOVED
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        button = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progress_bar);
        redirect = findViewById(R.id.redirect);

        // button's onclick listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username;
                String email;
                String password;

                // Progress bar visibility
                progressBar.setVisibility(View.VISIBLE);

                // getting the String from the editText username and password
//                username = String.valueOf(editTextUsername.getText());        MIGHT BE REMOVED
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

//                if(TextUtils.isEmpty(username)) {     MIGHT BE REMOVED
//                    Toast.makeText(activity_register.this, "Enter your Username", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(activity_register.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(activity_register.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Creating the user in firebase Auth
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.INVISIBLE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG_REGISTER", "createUserWithEmail:success");
                                    // FirebaseUser user = mAuth.getCurrentUser(); DON'T FORGET
                                    // updateUI(user);  DON'T FORGET
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG_REGISTER", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(activity_register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                   // updateUI(null);   DON'T FORGET
                                }
                            }
                        });

                return;
            }
        });

        // redirect's onclick listener
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}