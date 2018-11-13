package com.example.kom.dostawca;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    String [] Dane = new String[100];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dane = getIntent().getStringArrayExtra("Dane");
        NextActivity();
    }
    public void NextActivity()
    {
        if(Dane[0].equals("MAPA"))
            MapaStart();
        else if(Dane[0].equals("PUNKTY"))
            PunktyStart();
        else if(Dane[0].equals("RAPORT"))
            RaportStart();
        else if(Dane[0].equals("LOGIN"))
            LoginStart();
    }
    public void LoginStart()
    {
        Intent intent = new Intent(this, LoginActivity.class);;
        startActivity(intent);
    }
    public void MapaStart()
    {
        Intent intent = new Intent(this, PKT.class);
        intent.putExtra("Dane", Dane);
        startActivity(intent);
    }
    public void PunktyStart()
    {
        Intent intent = new Intent(this, PunktActivity.class);
        intent.putExtra("Dane", Dane);
        startActivity(intent);
    }
    public void RaportStart()
    {
        Intent intent = new Intent(this, RaportActivity.class);;
        startActivity(intent);
    }


}
