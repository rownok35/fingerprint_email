package com.example.myfingerprintauthenticateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private FingerprintManager fingerprintManager;
    private FingerprintManager.AuthenticationCallback authenticationCallback;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textview);
        imageView=findViewById(R.id.imageView);
        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button_email);
        imageView.setImageResource(R.drawable.img3);
        fingerprintManager=(FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editText.getText().toString().trim();
                Random rand = new Random();

                // Generate random integers in range 0 to 999
                int rand_int1 = rand.nextInt(1000);
                String pass= Integer.toString(rand_int1);
                String Subject="Password for Voting";

                JavaMailAPI javaMailAPI= new JavaMailAPI(MainActivity.this,email,Subject,pass);
                javaMailAPI.execute();

            }
        });
        authenticationCallback=new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                textView.setText("Error");
                imageView.setImageResource(R.drawable.img2);
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                textView.setText("Help");
                imageView.setImageResource(R.drawable.img3);
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                textView.setText("Success");
                imageView.setImageResource(R.drawable.img1);
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                textView.setText("Failed");
                imageView.setImageResource(R.drawable.img2);
                super.onAuthenticationFailed();
            }
        };


    }

    public void scanButton(View v)
    {
            fingerprintManager.authenticate(null,null,0,authenticationCallback,null);
    }
}
