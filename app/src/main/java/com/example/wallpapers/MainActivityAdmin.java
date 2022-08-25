package com.example.wallpapers;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wallpapers.fragmentosAdmin.HomeAdmin;
import com.example.wallpapers.fragmentosAdmin.ListenAdmin;
import com.example.wallpapers.fragmentosAdmin.ProfileAdmin;
import com.example.wallpapers.fragmentosAdmin.RegisterAdmin;
import com.google.android.material.navigation.NavigationView;

public class MainActivityAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        Toolbar toolbar = findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_Admin);

        NavigationView navigationView = findViewById(R.id.nav_viewAdmin);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Fragmento por defecto
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                    new HomeAdmin()).commit();
            navigationView.setCheckedItem(R.id.HomeAdmin);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.HomeAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                        new HomeAdmin()).commit();
                break;
            case R.id.ProfileAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                        new ProfileAdmin()).commit();
                break;
            case R.id.RegisterAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                        new RegisterAdmin()).commit();
                break;
            case R.id.ListenAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                        new ListenAdmin()).commit();
                break;
            case R.id.exit:
                Toast.makeText(this, "Cerraste sesion", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}