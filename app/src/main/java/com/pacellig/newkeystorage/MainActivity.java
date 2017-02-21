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

public class MainActivity extends AppCompatActivity {
    EditText et_psw = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_psw = (EditText)findViewById(R.id.main_password);
        // retrieve shared preferences ( if any )
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE);
        String saved_psw = sharedPreferences.getString("PSW", "PSW");
        if ( saved_psw.compareTo("PSW")==0){
            // open activity to edit new password
            Intent i = new Intent(this, NewPswActivity.class);
            startActivity(i);
        }
    }

    /**
     * Reacts to click on OK button
     * @param view
     */
    public void onClickMainButton(View view){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE);
        String saved_psw = sharedPreferences.getString("PSW", "PSW");
        // check password
        String input_psw_hash = NewPswActivity.sha512computer(et_psw.getText().toString());
        if ( input_psw_hash != null && saved_psw.compareTo(input_psw_hash) == 0 ){
            // password match
            Intent i = new Intent(this, PostLoginActivity.class);
            startActivity(i);
        } else {
            // no match
            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
            // TODO block user for x minutes after y wrong attempts
            // TODO password forgotten feature
        }
    }
}
