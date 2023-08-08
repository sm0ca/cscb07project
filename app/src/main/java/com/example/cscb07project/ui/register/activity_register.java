package com.example.cscb07project.ui.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.ui.login.activity_login;
import com.google.android.material.textfield.TextInputEditText;


public class activity_register extends AppCompatActivity implements activity_register_contract.View {

    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextStoreName;
    private ImageView storeLogo;
    private Uri storeLogoUri = null;
    private Button button;
    private TextView redirect;
    private activity_register_contract.Presenter presenter;
    private ProgressBar ProgressBar;
    private RadioGroup isOwner_option;
    private int isOwnerId = 0;

    public int getRadioButtonRegisterOwner() { return R.id.radioButton_register_owner; }
    public int getRadioButtonRegisterCustomer() { return R.id.radioButton_register_customer; }
    public Uri getStoreLogoUri() {
        return storeLogoUri;
    }

    // other functions
    public void progressBarVisibility (int mode) {
        ProgressBar.setVisibility(mode);
    }
    public void setStoreLogo(Uri uri) {
        storeLogo.setImageURI(uri);
    }

    // change activity if logged in
    @Override
    public void loggedIn() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new activity_register_presenter(this);

        // check if logged in
        presenter.doCheckLoggedIn();

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextStoreName = findViewById(R.id.store_name);

        button = findViewById(R.id.register_button);
        storeLogo = findViewById(R.id.store_logo);
        ProgressBar = findViewById(R.id.progress_bar);
        redirect = findViewById(R.id.redirect);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editTextPassword.getText());
                String storeName = String.valueOf(editTextStoreName.getText());

                // make progress bar visible after click button
                ProgressBar.setVisibility(View.VISIBLE);

                presenter.doRegisterEmail(email, password, storeName, isOwnerId);

            }
        });

        // onClickListener for storeLogo to get storeLogo's image
        storeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 69);

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




        // check if owner or customer
        isOwner_option = findViewById(R.id.radioButton_register);
        isOwner_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isOwnerId = i;

                // change storeName visibility
                if(isOwnerId == R.id.radioButton_register_owner) {
                    editTextStoreName.setVisibility(View.VISIBLE);
                    editTextStoreName.setHint(getResources().getString(R.string.store_name_text));
                    storeLogo.setVisibility(View.VISIBLE);
                }
                else {
                    editTextStoreName.setVisibility(View.GONE);
                    editTextStoreName.setHint(null);
                    storeLogo.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 69 && resultCode == RESULT_OK && data.getData() != null) { //nice!
            storeLogoUri = data.getData();
            setStoreLogo(storeLogoUri);
        }
    }
}