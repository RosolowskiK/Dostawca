package com.example.kom.dostawca;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.sql.SQLException;
import java.util.HashMap;

public class PunktActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AsyncResponse
{
    String [] Dane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punkt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Dane=getIntent().getStringArrayExtra("Dane");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        try
        {
            poprawnoscDanych("jeden", "dwa");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    void progress()
    {
        ProgressBar progress=(ProgressBar)findViewById(R.id.progressBar);
        progress.setScaleY(4f);
        progress.setProgress(10);
    }

    void fillthelist(ArrayList<String> list)
    {
        Resources res = getResources();
        ListView group=(ListView) findViewById(R.id.list);


        group.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1, list));


        Dane[4] = Integer.toString(list.size()); // dodanei liczby punktow
        for(int i = 1; i < list.size(); i++)
        {
            Dane[4+i]=list.get(i);
        }
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
        getMenuInflater().inflate(R.menu.punkt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            Toast.makeText(PunktActivity.this ,"Ale jeszcze nie teraz",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        Intent intent;
        if(id==R.id.mapa)
        {
            Dane[0] = "MAPA";
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("Dane",Dane);
            startActivity(intent);
            Toast.makeText(PunktActivity.this, "Mapa", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.punkty)
        {
            Toast.makeText(PunktActivity.this, "Punkty Trasy", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.raport)
        {
            Dane[0] = "RAPORT";
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("Dane",Dane);
            startActivity(intent);
            Toast.makeText(PunktActivity.this, "Punkty na trasie", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.logowanie)
        {
            Dane[0] = "LOGIN";
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("Dane",Dane);
            Toast.makeText(PunktActivity.this, "Wylogowywanie....", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.about)
        {
            Toast.makeText(PunktActivity.this, "Aplikacja dzial i ma sie dobrze", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void poprawnoscDanych(String login,String haslo) throws SQLException, ClassNotFoundException
    {
        HashMap postData = new HashMap();
        postData.put("btnLogin", "Login");
        postData.put("mobile", "android");
        postData.put("txtUsername", login);
        postData.put("txtPassword", haslo );

        PostResponseAsyncTask loginTask =
                new PostResponseAsyncTask(PunktActivity.this, postData,
                        PunktActivity.this);
        loginTask.execute("http://mudle.000webhostapp.com/punkty.php?id=+1");//+login+"'+&haslo=+'"+haslo+"'");
    }
    @Override
    public void processFinish(String s)
    {
        tworzenieListy(s);
    }

    void tworzenieListy(String s)
    {
        ArrayList<String> lista=new ArrayList<>();
        lista.add("");
        String  wyniki;
        int licznik=0;
        String Miasto="",Ulica="",Adres="";
        for(int i=0;i<s.length();i++)
        {
            if (s.charAt(i) == '.')
            {
                licznik += 1;
            }
            else if(s.charAt(i) == ',')
            {
                licznik=0;
                wyniki=Miasto+ " " + Ulica + " " + Adres;
                lista.add(wyniki);
                Miasto="";
                Ulica="";
                Adres="";
            }
            if(licznik == 0 && s.charAt(i) != '.'&& s.charAt(i) != ',')
            {
                Miasto+=s.charAt(i);
            }
            if(licznik == 2 && s.charAt(i) != '.'&& s.charAt(i) != ',')
            {
                Ulica += s.charAt(i);
            }
            if(licznik == 4 && s.charAt(i) != '.'&& s.charAt(i) != ',')
            {
                Adres+=s.charAt(i);
            }
        }
        //wyniki="Lublin Labedzia 13";
        //lista.add(wyniki);
        fillthelist(lista);
    }
}
