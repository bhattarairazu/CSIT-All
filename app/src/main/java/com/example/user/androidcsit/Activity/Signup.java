package com.example.user.androidcsit.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.androidcsit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by User on 9/11/2018.
 */

public class Signup extends AppCompatActivity {
    private final static String TAG = "Signup";
    private FirebaseAuth mAuth;
    private ProgressDialog mprogress;
    private EditText nEdittext_signup_uname,nEdittext_signup_email,nEdittext_signup_password,nEditext_signup_cpassword;
    private TextView nTextview_signinbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupp);
        nEdittext_signup_uname =(EditText) findViewById(R.id.mEdittext_signup_username);
        nEdittext_signup_email =(EditText) findViewById(R.id.mEdittext_signup_email);
        nEdittext_signup_password =(EditText) findViewById(R.id.mEdittext_signup_password);
        nEditext_signup_cpassword =(EditText) findViewById(R.id.mEdittext_signup_cpassword);
        nTextview_signinbtn =(TextView) findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();
        mprogress = new ProgressDialog(this);
        nTextview_signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = nEdittext_signup_uname.getText().toString();
                String email = nEdittext_signup_email.getText().toString();
                String password = nEdittext_signup_password.getText().toString();
                String cpas = nEditext_signup_cpassword.getText().toString();

                //confirm pass and pass match check later
                    if(!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)&& !TextUtils.isEmpty(cpas)){
                        if(password.equals(cpas)){
                            mprogress.setTitle("Registering User");
                            mprogress.setMessage("Please wait While we create your account");
                            mprogress.setCanceledOnTouchOutside(false);
                            mprogress.show();
                            register_user(uname,email,password);
                        }else{
                            Toast.makeText(Signup.this, "Your password and confirm password doesnot match.Please .try again", Toast.LENGTH_SHORT).show();
                            nEdittext_signup_password.setText("");
                            nEditext_signup_cpassword.setText("");
                        }

                    }else{
                        Toast.makeText(Signup.this, "Please Fill up all field", Toast.LENGTH_SHORT).show();
                    }

            }
        });


    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//            startActivity();
//        }
//    }
    private void register_user(String user,String emails,String pass){
        mAuth.createUserWithEmailAndPassword(emails, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mprogress.dismiss();
                            Toast.makeText(Signup.this, "Account Created Successfully.Please Login to Continu.", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            //startActivity(new Intent(Signup.this,Loginn.class));
                            finish();
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            mprogress.hide();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signup.this, "Cannot Signin.Please check your field and try again!",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
