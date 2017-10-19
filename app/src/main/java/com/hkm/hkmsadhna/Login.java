package com.hkm.hkmsadhna;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

ProgressBar progressBar;
    private static final int RC_SIGN_IN = 2;
    SignInButton signInButton;
    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String ss;
    String value;
    ProgressDialog dialog;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        Log.v("Testing","1");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.v("Testing","2");
        dialog = new ProgressDialog(Login.this);
        dialog.setMessage("Please wait");

        if (isNetworkConnected()){
            Toast.makeText(Login.this, "Connection", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(Login.this, "No Connection", Toast.LENGTH_LONG).show();
        }


        SharedPreferences settings = getSharedPreferences("Pre", MODE_PRIVATE);
        value = settings.getString("flag", "");

        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.v("Testing","3");

                if (firebaseAuth.getCurrentUser() != null) {
                    Log.v("Testing","4");

                    if (value.equals("yes")) {
                        Log.v("Testing","5");

                        startActivity(new Intent(Login.this, MainActivity.class));
                    } else {
                        Log.v("Testing","6");

                        String email = firebaseAuth.getCurrentUser().getEmail();
                    SharedPreferences settings = getSharedPreferences("Pre", MODE_PRIVATE);
                    // Writing data to SharedPreferences
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("email", email);
                        ss = ConvertEmail(email);
                        editor.putString("convertedEmail",ss);
                        editor.commit();


                    myRef = FirebaseDatabase.getInstance().getReference().child("Permission");

                    ValueEventListener postListener1 = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get Post object and use the values to update the UI

                            if (dataSnapshot.hasChild(ss)) {
                                SharedPreferences settings = getSharedPreferences("Pre", MODE_PRIVATE);
                                // Writing data to SharedPreferences
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("flag", "yes");
                                editor.commit();
                                dialog.dismiss();
                                Toast.makeText(Login.this, "Child extst", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this, MainActivity.class));

                            } else {
                                Toast.makeText(Login.this, "Please contact the admin for access", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };
                    myRef.addValueEventListener(postListener1);


                }
            }

                else{
                    Log.v("Testing","7");

                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Log.v("Testing","8");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(Login.this,"Hello",Toast.LENGTH_SHORT).show();
                        Log.v("Testing","9");

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Testing","10");
                if (isNetworkConnected()){
                signIn();}
                else {
//                    Snackbar snackbar = Snackbar
//                            .make(null, "No internet connection", Snackbar.LENGTH_LONG);
//                    snackbar.show();
                    Toast.makeText(Login.this,"No internet connection",Toast.LENGTH_SHORT).show();


                }
                Log.v("Testing","11");

            }
        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.v("Testing","12");

        return cm.getActiveNetworkInfo() != null;
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.v("Testing","13");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("Testing","14");
        dialog.show();

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.v("Testing","15");

            if (result.isSuccess()) {        Log.v("Testing","16");

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(Login.this,"Somwthing went wrong",Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.v("Testing","17");

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v("Testing","18");

                        if (task.isSuccessful()) {        Log.v("Testing","19");

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {        Log.v("Testing","20");

                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    String ConvertEmail(String s){
        String newString = "";
        for (int i = 0;i < s.length();i++){
            if (s.charAt(i) == '.' && s.charAt(i+1) == 'c' && s.charAt(i+2) == 'o' && s.charAt(i+3) == 'm'){
                break;
            }
            else{
                newString = newString + s.charAt(i);
                Log.v("Email",newString);
            }
        }
        return newString;
    }

}
