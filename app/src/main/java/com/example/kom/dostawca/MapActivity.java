package com.example.kom.dostawca;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback , NavigationView.OnNavigationItemSelectedListener
{
//--------------------------------------
    private GoogleMap mMap;
    LocationManager lm; //Inicjujemy menadżer lokalizacji
    Criteria kr; //Klasa służąca do wybierania odpowiedniego menadżera lokalizacji
    Location loc; //Służy do przekazywania położenia
    String najlepszyDostawca;
    TextView t1;
    TextView t2;
//--------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        odswiez();

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
    //--------------------------------------------
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        LatLng aktualnaLokalizacja = new LatLng(52, 21);

        kr = new Criteria(); //Konstruktor dla klasy Ctriteria
        lm = (LocationManager) getSystemService(LOCATION_SERVICE); //Pobieramy usługę lokalizacji
        najlepszyDostawca = lm.getBestProvider(kr, true); //Chcemy najlepszego dostawcę
        loc = lm.getLastKnownLocation(najlepszyDostawca); //Pobieramy ostatnio znaną lokalizację
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            return;
        }
        lm.requestLocationUpdates(najlepszyDostawca, 1000, 1, this); //Zapytanie o aktualizację pozycji
        if (!(loc == null)) //Jeśli loc jest null to wystąpiłby wyjątek
        {
            aktualnaLokalizacja= new LatLng( loc.getLatitude(),loc.getLongitude());
        }
        // Add a marker in Sydney and move the camera
        LatLng lublin = new LatLng(51.23, 22.56);
        mMap.addMarker(new MarkerOptions().position(lublin).title("Marker in Poland"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lublin));
        mMap.setMinZoomPreference(13.0f);


    }

    /// 4 metody do uzyskiwania aktualnej lokazlizacji i okreslania dostawcy
    @Override
    public void onLocationChanged(Location location)
    {
        odswiez();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {
        odswiez();
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        t1.setText("");
        t2.setText("");
    }

    private void odswiez()
    {

        kr = new Criteria(); //Konstruktor dla klasy Ctriteria
        lm = (LocationManager) getSystemService(LOCATION_SERVICE); //Pobieramy usługę lokalizacji
        najlepszyDostawca = lm.getBestProvider(kr, true); //Chcemy najlepszego dostawcę
        loc = lm.getLastKnownLocation(najlepszyDostawca); //Pobieramy ostatnio znaną lokalizację
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            return;
        }
        lm.requestLocationUpdates(najlepszyDostawca, 1000, 1, this); //Zapytanie o aktualizację pozycji
        if (!(loc == null)) //Jeśli loc jest null to wystąpiłby wyjątek
        {
            // t1.setText("Dostawca lokalizacji: " + najlepszyDostawca); //Wypełniamy pola tekstowe
            // t2.setText("Długość geograficzna: " + String.valueOf(loc.getLongitude()) + " Szerokość geograficzna: " + String.valueOf(loc.getLatitude()));
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume(); // Always call the superclass method first
        odswiez();
    }

    @Override
    protected void onPause()
    {
        super.onPause(); // Always call the superclass method first
        lm.removeUpdates(this); //Usuwamy aktualizację lokalizacji
    }
//-----------------------------------------------------------------------------------------------

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
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            Toast.makeText(MapActivity.this ,"Ale jeszcze nie teraz",Toast.LENGTH_LONG).show();
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
            intent = new Intent(context, LoginActivity.class);
            startActivity(intent);

            Toast.makeText(MapActivity.this, "Wylogowywanie...", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.punkty)
        {
            intent = new Intent(context, PunktActivity.class);
            startActivity(intent);

            Toast.makeText(MapActivity.this, "Punkty na trasie", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.raport)
        {
            intent = new Intent(context, RaportActivity.class);
            startActivity(intent);

            Toast.makeText(MapActivity.this, "Raport", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.mapa)
        {

            Toast.makeText(MapActivity.this, "Mapa", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.about)
        {
            Toast.makeText(MapActivity.this, "Aplikacja dzial i ma sie dobrze", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
