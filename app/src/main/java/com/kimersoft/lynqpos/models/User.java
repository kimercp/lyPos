package com.kimersoft.lynqpos.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by souhaibbenfarhat on 8/31/17.
 */

public class User {

    private String walletKey;
    private String email;
    private String lastname;
    private String mnemonic;
    private String name;
    private String password;
    private String type;
    private String token;
    private String deviceToken;

    public User() {
    }

    public User(String walletKey, String email, String lastname, String mnemonic, String name, String password, String type) {

        this.walletKey = walletKey;
        this.email = email;
        this.lastname = lastname;
        this.mnemonic = mnemonic;
        this.name = name;
        this.password = password;
        this.type = type;
    }


    public String getWalletKey() {
        if(walletKey!=null)
            return walletKey.trim();
        return walletKey;
    }

    public void setWalletKey(String walletKey) {
        this.walletKey = walletKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email != null ? email.equals(user.email) : user.email == null;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    public static void setCurrentUserAfterLogin(User user, Activity a){

        SharedPreferences sharedpreferences = a.getSharedPreferences("user_credentials", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("token", user.getToken());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.apply();

    }
    public static void setCurrentUserAfterInfo(User user, Activity a){

        SharedPreferences sharedpreferences = a.getSharedPreferences("user_credentials", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("email", user.getEmail());
        editor.putString("walletKey", user.getWalletKey());
        editor.putString("name", user.getName());
        editor.putString("lastName", user.getLastname());
        editor.apply();

    }
    public static void setSharedPrefPassword(String pass, Activity a){

        SharedPreferences sharedpreferences = a.getSharedPreferences("user_credentials", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("password", pass);
        editor.apply();

    }
    public static void setDeviceToken(String token, Context a){

        SharedPreferences sharedpreferences = a.getSharedPreferences("user_credentials", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("deviceToken", token);
        editor.apply();

    }
    public static String getDeviceToken(Activity a){

        SharedPreferences sharedpreferences = a.getSharedPreferences("user_credentials", Activity.MODE_PRIVATE);
        return sharedpreferences.getString("deviceToken", null);

    }
    public static User getCurrentUser(Activity a){
        SharedPreferences sharedpreferences = a.getSharedPreferences("user_credentials", Activity.MODE_PRIVATE);
        User u =new User();
        u.setEmail(sharedpreferences.getString("email", ""));
        u.setPassword(sharedpreferences.getString("password", ""));
        u.setToken(sharedpreferences.getString("token", ""));
        u.setWalletKey(sharedpreferences.getString("walletKey",""));
        u.setLastname(sharedpreferences.getString("lastName",""));
        u.setName(sharedpreferences.getString("name",""));
        System.out.println("walletKey"+u.getWalletKey());

        return u;

    }
    public static User getCurrentUser(Context a){
        SharedPreferences sharedpreferences = a.getSharedPreferences("user_credentials", Activity.MODE_PRIVATE);
        User u =new User();
        u.setEmail(sharedpreferences.getString("email", ""));
        u.setPassword(sharedpreferences.getString("password", ""));
        u.setToken(sharedpreferences.getString("token", ""));
        u.setWalletKey(sharedpreferences.getString("walletKey",""));
        u.setLastname(sharedpreferences.getString("lastName",""));
        u.setName(sharedpreferences.getString("name",""));
        System.out.println("walletKey"+u.getWalletKey());

        return u;

    }
    public static boolean logOut(Activity a){
        SharedPreferences sharedpreferences = a.getSharedPreferences("user_credentials", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        return editor.clear().commit();
        // editor.apply();
    }

    @Override
    public String toString() {
        return "User{" +
                "walletKey='" + walletKey + '\'' +
                ", email='" + email + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mnemonic='" + mnemonic + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
