package com.pacellig.newkeystorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NewPswActivity extends AppCompatActivity {
    EditText et_psw, et_psw_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_psw);
        et_psw = (EditText)findViewById(R.id.new_psw_psw);
        et_psw_confirm = (EditText)findViewById(R.id.new_psw_confirm);
    }

    public void onClickNewPassword(View view) {
        String psw = et_psw.getText().toString();
        String confirm = et_psw_confirm.getText().toString();

        if ( psw.length() < 8 || !psw.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]+)")){
            Toast.makeText(this.getApplicationContext(), getString(R.string.password_constraints), Toast.LENGTH_SHORT).show();
        } else {
            if ( psw.compareTo(confirm)!=0 ){
                Toast.makeText(this.getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                Context context = getApplicationContext();
                SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(getString(R.string.shared_pref_file),
                        Context.MODE_PRIVATE).edit();
                String hashedPsw = sha512computer(psw);
                if ( hashedPsw != null ){
                    sharedPreferences.putString("PSW", hashedPsw);
                    sharedPreferences.apply();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                } else {
                    // error
                }
            }
        }

    }

    public static String sha512computer(String psw){
        Log.d("SHA512", psw);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(psw.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for ( int i=0; i<bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
