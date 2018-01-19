package com.example.kom.dostawca;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginStart();
        this.finish();
    }

    public void LoginStart()
    {
        Context context;
        Intent intent;

        context = getApplicationContext();
        intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }
}
