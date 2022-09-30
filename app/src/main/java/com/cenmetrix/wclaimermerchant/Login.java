package com.cenmetrix.wclaimermerchant;

import static com.cenmetrix.wclaimermerchant.global.GlobalVariables.db;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cenmetrix.wclaimermerchant.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    TextView userEmailLoginErrTV, passwordLoginErrTV;
    EditText userEmailLoginET, passwordLoginET;
    ImageButton loginIB;
    TextView registerClickTV, txtForgotPasswordClick;
    ProgressBar progress_circular;

    FirebaseAuth mAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (FirebaseApp.getApps(this).size() == 0) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        mAuth = FirebaseAuth.getInstance();

        Log.e(TAG, "onCreate: " + FirebaseApp.getApps(this).size());
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        userEmailLoginErrTV = findViewById(R.id.userEmailLoginErrTV);
        passwordLoginErrTV = findViewById(R.id.passwordLoginErrTV);
        userEmailLoginET = findViewById(R.id.userEmailLoginET);
        passwordLoginET = findViewById(R.id.passwordLoginET);
        loginIB = findViewById(R.id.loginIB);
        registerClickTV = findViewById(R.id.registerClickTV);
        txtForgotPasswordClick = findViewById(R.id.txtForgotPasswordClick);
        progress_circular = findViewById(R.id.progress_circular);
        progress_circular.setVisibility(View.GONE);
        initEffects();
        initClicks();


        if (mAuth.getCurrentUser() != null) {
            redirectMainDashboard();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEffects() {
        loginIB.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    loginIB.setImageResource(R.drawable.login_btn_background_pressed);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    loginIB.setImageResource(R.drawable.login_btn_background);
                    break;
                }
            }
            return false;
        });
    }

    private void initClicks() {
        loginIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authenticateUser();
            }
        });


        registerClickTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClickTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Login.this,  Register.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    private void authenticateUser() {
        isProgress(true);
        String email = userEmailLoginET.getText().toString();
        String password = passwordLoginET.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please all fields !", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uid = task.getResult().getUser().getUid();
                    Log.e(TAG, "onComplete: " + task.getResult().getUser().getUid());
                    DocumentReference docRef = db.collection("merchant_users").document(uid);
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            UserModel um = documentSnapshot.toObject(UserModel.class);
                            if (um != null) {
                                if (!um.getUser_Role().trim().equals("Merchant")) {
                                    mAuth.signOut();
                                    Log.e(TAG, "onSuccess: " + um.getUser_Role());
                                    isProgress(false);
                                } else {
                                    redirectMainDashboard();
                                }
                            } else {
                                Toasty.warning(Login.this, "Authentication Failed !", Toast.LENGTH_SHORT).show();
                            }
                            isProgress(false);
                        }
                    });


                } else {
                    Toasty.warning(Login.this, "Authentication Failed !", Toast.LENGTH_SHORT).show();
                    isProgress(false);
                }

            }
        });




    }


    private void redirectMainDashboard() {
        isProgress(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void isProgress(Boolean isProgress) {
        if (isProgress) {
            loginIB.setVisibility(View.GONE);
            progress_circular.setVisibility(View.VISIBLE);
        } else {
            loginIB.setVisibility(View.VISIBLE);
            progress_circular.setVisibility(View.GONE);
        }
    }
/*
    private void LoginWithoutAuth(){
        UserModel reqUser = new UserModel();
        String user_email = userEmailLoginET.getText().toString();
        String password = passwordLoginET.getText().toString();


//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.child("users").hasChild(mobile_no)) {
//                            UserModel resUser = snapshot.child("users").child(mobile_no).getValue(UserModel.class);
//
//                            Log.e(TAG, resUser.getUser_name());
//                            Log.e(TAG, resUser.getUser_password());
//
//                            Log.e(TAG, "onDataChange: " + password);
//                            if (password.equals(resUser.getUser_password())) {
//                                Log.e(TAG, "onDataChange: ");
//
//                                loginCheck(resUser, mobile_no);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
        isProgress(true);
        CollectionReference citiesRef = db.collection("users");
        Query query = citiesRef
                .whereEqualTo("email", user_email)
                .whereEqualTo("password", password);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.e(TAG, "onComplete: SUCCESS");
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Log.e(TAG, "onComplete: " + documentSnapshot.getData());
                    }
                    if (!task.getResult().isEmpty()) {
                        isProgress(false);
                        loginCheck(reqUser);
                    } else {
                        isProgress(false);
                        Toasty.warning(Login.this, "Wrong Credential !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "onComplete: NOT SUCCESS");
                    isProgress(false);
                    Log.d(TAG, task.getException().getMessage()); //Never ignore potential errors!
                }
            }
        });


    }
    private void loginCheck(UserModel resUser) {

        //             databaseReference.child("users").child(email).child("password").setValue(password);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Login.this, MainActivity.class);
//        intent.putExtra("mobile_no", resUser.getUser_mobile());
//        intent.putExtra("user_name", resUser.getUser_name());
        startActivity(intent);
        finish();

    }
*/
}