package com.example.cscb07project.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.ui.register.activity_register;
import com.example.cscb07project.ui.tools.MainStart;
import com.google.android.material.textfield.TextInputEditText;

public class activity_login extends AppCompatActivity implements activity_login_contract.View {

    TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    Button button;
    TextView redirect;
    ProgressBar progressBar;
    activity_login_contract.Presenter presenter;

    @Override
    public void progressBarVisibility(int i) {
        progressBar.setVisibility(i);
    }

    // change activity to MainActivity
    @Override
    public void loggedIn() {
        MainStart.switchToMain(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new activity_login_presenter(this);

        // checking if logged in
        presenter.doCheckLoggedIn();


        // Initialize
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        button = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progress_bar);
        redirect = findViewById(R.id.redirect);

        // button's onclick listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editTextPassword.getText());

                // Progress bar visibility
                progressBar.setVisibility(View.VISIBLE);

                presenter.doLoginEmail(email, password);

            }
        });

        // redirect onclick listener to register activity
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_register.class);
                startActivity(intent);
                finish();
            }
        });
    }

}