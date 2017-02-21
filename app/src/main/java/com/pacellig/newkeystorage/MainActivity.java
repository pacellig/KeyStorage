package com.pacellig.newkeystorage;

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
    }

    /**
     * Reacts to click on OK button
     * @param view
     */
    public void onClickMainButton(View view){
        String psw = et_psw.getText().toString();
        // TODO remove log and Toast
        Toast.makeText(this.getApplicationContext(), psw, Toast.LENGTH_LONG).show();
        Log.d("Main", psw);
    }
}
