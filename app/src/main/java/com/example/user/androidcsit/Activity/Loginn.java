package com.example.user.androidcsit.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//
//import com.facebook.login.Login;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
import com.example.user.androidcsit.MainActivity;
import com.example.user.androidcsit.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 9/11/2018.
 */

public class Loginn extends AppCompatActivity {
    private TextView mTextview_login_sup,mTextview_singinbtn;
    private EditText nEditext_login_uname,nEditext_login_pssword;
    private ProgressDialog mprogress;
    private final static String TAG = "login";
    private FirebaseAuth mAuth;
    Button  mmbtl;
    FirebaseFirestore firebaseFirestore;
    private CallbackManager callBackManager;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_loginn);
        mmbtl =(Button) findViewById(R.id.login_button);
//        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        mTextview_login_sup =(TextView) findViewById(R.id.mTextview_login_signup);
        nEditext_login_uname =(EditText) findViewById(R.id.mEdittext_login_uname);
        nEditext_login_pssword =(EditText) findViewById(R.id.mEdittext_login_pssword);
        mTextview_singinbtn =(TextView) findViewById(R.id.btnSignIn);
        mprogress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String images = user.getPhotoUrl().toString();
                    String names = user.getDisplayName();
                    Log.d(TAG, "onAuthStateChanged: ssssssssss" + images + "sdfsff" + names);

                    startActivity(new Intent(Loginn.this, MainActivity.class).putExtra("name", names).putExtra("images", images));

                }
            }
        };
       fblogin();
        // [END initialize_auth]

        // [START initialize_fblogin]
        // Initialize Facebook Login button
//        mmbtl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callBackManager = CallbackManager.Factory.create();
//                final LoginButton loginbtn = new LoginButton(v.getContext());
//                loginbtn.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        attachCredToFirebase(FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken()));
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//                        Toast.makeText(Loginn.this, "errr"+error, Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "onError: jjjjjjjjjjjj"+error);
//                    }
//                });
//            }
//        });

        mTextview_singinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = nEditext_login_uname.getText().toString();
                String password = nEditext_login_pssword.getText().toString();
                if(!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(password)){

                        mprogress.setTitle("Loggin In");
                        mprogress.setMessage("Please wait While we check your cerenditals");
                        mprogress.setCanceledOnTouchOutside(false);
                        mprogress.show();
                       login_user(uname,password);


                }else{
                    Toast.makeText(Loginn.this, "Please Fill up all field", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mTextview_login_sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loginn.this,Signup.class));
            }
        });
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.user.csitandroid",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    public void fblogin(){
        callBackManager = CallbackManager.Factory.create();

        final LoginButton loginbtn = new LoginButton(Loginn.this);
        loginbtn.clearPermissions();
        if(loginbtn!=null){
            loginbtn.setReadPermissions("email","public_profile");
            loginbtn.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    attachCredToFirebase(FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken()));
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(Loginn.this, "errr"+error, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onError: jjjjjjjjjjjj"+error);
                }
            });
        }
        mmbtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbtn.callOnClick();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //For fb login
       callBackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void attachCredToFirebase(AuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        startActivity(new Intent(Loginn.this,MainActivity.class));
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();

                        if (e instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(Loginn.this, "Email address in use by another account. Try another option.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Loginn.this, "Unable to login.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



    private void login_user(String uname, String pass){
        mAuth.signInWithEmailAndPassword(uname, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mprogress.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            startActivity(new Intent(Loginn.this, MainActivity.class));
                            //FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            mprogress.hide();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Loginn.this, "Cannot SignIn Please Try Again Later.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
