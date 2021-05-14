package com.example.pocketnews_277.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketnews_277.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    final String TAG = "SignUp";
    private Button gotToLoginButton;
    private FirebaseAuth mAuth;
    private Button SignUpButton;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        // EditText username
        TextInputLayout userNameInput = findViewById(R.id.emailLogin);
        TextInputLayout emailInput = findViewById(R.id.emailReg);
        TextInputLayout passwordInput = findViewById(R.id.password);


        gotToLoginButton = (Button) findViewById(R.id.goToLoginPage);
        gotToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSignUpIntent = new Intent(SignUp.this, Login.class);
                startActivity(goToSignUpIntent);

            }
        });

        SignUpButton = (Button) findViewById(R.id.registerAccountButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp(userNameInput, emailInput, passwordInput);

            }
        });

    }

    private void signUp(TextInputLayout userNameInput, TextInputLayout emailInput, TextInputLayout passwordInput) {
        String userName = userNameInput.getEditText().getText().toString();
        String email = emailInput.getEditText().getText().toString();
        String password = passwordInput.getEditText().getText().toString();

        if (userName.isEmpty()) {
            userNameInput.setError("Please enter User name");
            return;

        } else if (email.isEmpty()) {
            emailInput.setError("Please enter Email");
            return;

        } else if (password.isEmpty()) {
            passwordInput.setError("Please enter password");
            return;

        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            db = FirebaseFirestore.getInstance();
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("user_name", userName);
                            db.collection(user.getUid()).document("users").set(userInfo).addOnSuccessListener(
									new OnSuccessListener<Void>() {
										@Override
										public void onSuccess(Void aVoid) {
											Toast.makeText(SignUp.this, "Registration is successful.",
													Toast.LENGTH_SHORT).show();
										}
									}
							).addOnFailureListener(
									new OnFailureListener() {
										@Override
										public void onFailure(@NonNull Exception e) {
											Log.w(TAG, "Error adding document", e);
											Toast.makeText(SignUp.this, "Unable to register user!",
													Toast.LENGTH_SHORT).show();
										}
									}
							);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }
}