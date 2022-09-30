package com.cenmetrix.wclaimermerchant.global;

import android.app.Activity;
import android.content.Intent;

import com.cenmetrix.wclaimermerchant.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GlobalMethods {
    public static FirebaseAuth auth;
    public static FirebaseUser currentUser;

    public static void FirebaseAuthRequest(Activity activity) {
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(activity, Login.class);
            activity.startActivity(intent);
            activity.finish();
            return;
        }
    }

    public static String getCurrentDate() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
