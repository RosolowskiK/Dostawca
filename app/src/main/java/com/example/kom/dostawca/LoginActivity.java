package com.example.kom.dostawca;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AsyncResponse
{
    EditText editText_haslo;
    EditText editText_login;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
///--------
        editText_haslo=(EditText)findViewById(R.id.editText_haslo);
        Button button=(Button)findViewById(R.id.show1);


        //podswietlanie hasla
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {

                    case MotionEvent.ACTION_DOWN:
                        editText_haslo.setInputType(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        editText_haslo.setInputType(129);
                        break;
                }

                return true;
            }
        });


        //------
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
//-------------------
    public void SaveText(View view) throws SQLException, ClassNotFoundException
    {
        text = (TextView)findViewById(R.id.text);
        editText_haslo = (EditText)findViewById(R.id.editText_haslo);
        editText_login = (EditText)findViewById(R.id.editText_login);

        String loginText = editText_login.getText().toString();
        String hasloText = editText_haslo.getText().toString();

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            connected = true;
        }
        else
            connected = false;

        if(connected==true)
        {
            text.setVisibility(View.GONE);
            poprawnoscDanych(loginText, hasloText);
        }
        else
        {
            text.setVisibility(View.VISIBLE);
            text.setText("Brak połączeia z internem");
        }

    }

    void poprawnoscDanych(String login,String haslo) throws SQLException, ClassNotFoundException
    {
        HashMap postData = new HashMap();
        postData.put("btnLogin", "Login");
        postData.put("mobile", "android");
        postData.put("txtUsername", login);
        postData.put("txtPassword", haslo );

        PostResponseAsyncTask loginTask =
                new PostResponseAsyncTask(LoginActivity.this, postData,
                        LoginActivity.this);
        loginTask.execute("http://strapping-silicon.000webhostapp.com/login.php?login=+'"+login+"'+&haslo=+'"+haslo+"'");
    }

    @Override
    public void processFinish(String output)
    {
            if(output.equals("ok"))
            {
                text.setVisibility(View.GONE);
                editText_login.setText("");
                editText_haslo.setText("");
                MapsStart();
            }
            else
            {
                editText_login.setText("");
                editText_haslo.setText("");
                text.setText("Złe dane logowanie sproboj jeszcze raz");
                text.setVisibility(View.VISIBLE);
            }
    }

    public void MapsStart()
    {
        Context context;
        Intent intent;

        context = getApplicationContext();
        intent = new Intent(context, MapActivity.class);
        startActivity(intent);
    }


    ///----------------
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
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            Toast.makeText(LoginActivity.this ,"Ale jeszcze nie teraz",Toast.LENGTH_LONG).show();
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

        if(id==R.id.logowanie)
        {
            intent = new Intent(context, MainActivity.class);
            startActivity(intent);

            Toast.makeText(LoginActivity.this, "Wyjscie", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.about)
        {
            Toast.makeText(LoginActivity.this ,"Aplikacja dziala i ma sie dorbze ",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    ///---------------------------laczenie z serverem
}
