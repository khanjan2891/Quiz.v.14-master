package com.bvm.ui_example_4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Random;


public class Third extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    private ViewGroup rootView;
    NavigationView navigationView;

    Toolbar toolbar;
    ImageButton button;
    Button cancel, cancel1;
    View add, del, subjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        button = (ImageButton) findViewById(R.id.more);
        add = (View) findViewById(R.id.add_layout);
        del = (View) findViewById(R.id.del_layout);
        rootView = (ViewGroup) findViewById(R.id.subjects);
        subjects=(View)findViewById(R.id.subjects);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupmenu = new PopupMenu(Third.this, button);
                popupmenu.getMenuInflater().inflate(R.menu.sub_menu, popupmenu.getMenu());

                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(Third.this, "" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        if (menuItem.getItemId() == R.id.add_sub) {
                            add.setVisibility(View.VISIBLE);
                          subjects.setVisibility(View.INVISIBLE);
                            /* ADD Subject COde  */
                        } else if (menuItem.getItemId() == R.id.del_sub) {
                            del.setVisibility(View.VISIBLE);
                            /* ADD Subject COde */
                        }
                        return true;

                    }
                });
                popupmenu.show();

            }
        });



        /*-----hooks-----*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.sub_toolbar);
        navigationView.bringToFront();
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_assignment:
                Intent intent = new Intent(Third.this, Assignment.class);
                startActivity(intent);
                break;
            case R.id.nav_subject:
                Intent intent2 = new Intent(Third.this, Third.class);
                startActivity(intent2);
                break;
            case R.id.nav_quiz:
                Intent intent3 = new Intent(Third.this, quiz_main.class);
                startActivity(intent3);
                break;
            case R.id.nav_profile:
                Intent intent4 = new Intent(Third.this, Profile.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                Intent intent5 = new Intent(Third.this, MainActivity.class);
                startActivity(intent5);
                break;


        }

        return true;
    }

    public void cancel(View view) {
        cancel = (Button) findViewById(R.id.cancel);
        cancel1 = (Button) findViewById(R.id.cancel1);
        add = (View) findViewById(R.id.add_layout);
        del = (View) findViewById(R.id.del_layout);
        add.setVisibility(View.INVISIBLE);
        del.setVisibility(View.INVISIBLE);
        subjects.setVisibility(View.VISIBLE);


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


    public void addbutton(View view){
        subjects.setVisibility(View.VISIBLE);
        Button button1 = new Button(Third.this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int[] images = {R.drawable.orange_tree,R.drawable.pink_tree,R.drawable.colorful_tree,R.drawable.purple_tree,R.drawable.brown_tree};
        Random rand = new Random();
        button1.setBackgroundResource(images[rand.nextInt(images.length)]);
        button1.setText("Subject added");
        button1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 70);
        button1.setGravity(Gravity.CENTER_VERTICAL);
        button1.setTextColor(Color.BLACK);

params.leftMargin= 0;
        params.rightMargin= 0;
        params.topMargin=10;
        params.height=450;

        button1.setLayoutParams(params);
// params.addRule ... (there's a bunch you can add)

        rootView.addView(button1, params);
         cancel(view);

}
}