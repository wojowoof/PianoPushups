package com.wojones.pianopushups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMsg(View vw) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText edTxt = (EditText)findViewById(R.id.editText);
        String msg = edTxt.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, msg);
        startActivity(intent);
    }


    public void goNotePractice2(View vw) {
        Intent intent = new Intent(this, NotePractice2.class);
        startActivity(intent);
    }
}
