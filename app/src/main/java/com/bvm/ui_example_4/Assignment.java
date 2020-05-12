
package com.bvm.ui_example_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Assignment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    Toolbar toolbar;
    ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        button=(ImageButton)findViewById(R.id.more);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupmenu= new PopupMenu(Assignment.this,button);
                popupmenu.getMenuInflater().inflate(R.menu.sub_menu,popupmenu.getMenu());

                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {



                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(Assignment.this,""+ menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;

                    }
                });
                popupmenu.show();

            }
        });



        /*-----hooks-----*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view1);
        toolbar = findViewById(R.id.assignment_toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_menu,menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_assignment:
                Intent intent = new Intent(Assignment.this,Assignment.class);
                startActivity(intent);
                break;
            case R.id.nav_subject:
                Intent intent2 = new Intent(Assignment.this,Third.class);
                startActivity(intent2);
                break;
            case R.id.nav_quiz:
                Intent intent3 = new Intent(Assignment.this,quiz_main.class);
                startActivity(intent3);
                break;
            case R.id.nav_profile:
                Intent intent4 = new Intent(Assignment.this,Profile.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                Intent intent5 = new Intent(Assignment.this,MainActivity.class);
                startActivity(intent5);
                break;


        }

        return true;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }
}



