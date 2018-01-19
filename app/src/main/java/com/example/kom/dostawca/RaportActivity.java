package com.example.kom.dostawca;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Toast;

public class RaportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.raport_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            Toast.makeText(RaportActivity.this ,"Ale jeszcze nie teraz",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        Context context= getApplicationContext();;
        Intent intent;
        if(id==R.id.mapa)
        {
            intent = new Intent(context, MapActivity.class);
            startActivity(intent);
            Toast.makeText(RaportActivity.this, "Mapa.", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.punkty)
        {
            intent = new Intent(context, PunktActivity.class);
            startActivity(intent);
            Toast.makeText(RaportActivity.this, "Punkty na trasie", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.raport)
        {
            Toast.makeText(RaportActivity.this, "Raport", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.logowanie)
        {
            intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(RaportActivity.this, "logowanie ", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.about)
        {
            Toast.makeText(RaportActivity.this, "Aplikacja dzial i ma sie dobrze", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
   //--------------------------------------------MENU--------------------------------------------
    public void RadioGroup(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId())
        {
            case R.id. Bez:
                if (checked)
                {
                    Toast.makeText(this,"Raport nie bedzie generowany",Toast.LENGTH_LONG).show();
                }
                    break;
            case R.id.Email:
                if (checked)
                {
                    Toast.makeText(this,"Raport bedzie wyslany na Email pod koniec dostawy",Toast.LENGTH_LONG).show();
                }
                    break;
            case R.id.Pdf:
                if (checked)
                {
                    Toast.makeText(this,"Raport bedzie wygenerowany w PDF pod koniec dostawy",Toast.LENGTH_LONG).show();
                }
                    break;
            case R.id.Wyswietl:
                if (checked)
                {

                }
                break;
        }
    }
}
