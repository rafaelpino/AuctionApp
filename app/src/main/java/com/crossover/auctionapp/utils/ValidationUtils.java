package com.crossover.auctionapp.utils;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by rafaelpino on 12/14/16.
 */
public class ValidationUtils {
    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public static void showEmptyFieldMessage(View view){
        Snackbar.make(view, "This field is requiered", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
