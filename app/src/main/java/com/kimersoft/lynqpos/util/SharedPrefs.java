package com.kimersoft.lynqpos.util;

import android.app.Activity;

/**
 * Created by Gadour on 05/12/2017.
 */

public class SharedPrefs {

    private Activity mActivity;

    public SharedPrefs(Activity activity){
        this.mActivity = activity;
    }

    public static final String PREF_SIGN_IN = "prefSignIn";
    public static final String USER_TOKEN = "userToken";

   /* public void saveUserToken (String token){
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(PREF_SIGN_IN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }
*/
}
