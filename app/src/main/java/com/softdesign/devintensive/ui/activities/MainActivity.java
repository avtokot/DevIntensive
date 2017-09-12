package com.softdesign.devintensive.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.softdesign.devintensive.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mCallImg;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mCallImg = (ImageView) findViewById(R.id.call_img);
        mCallImg.setOnClickListener(this);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);

        setupToolbar();
        setupDrawer();

        if (savedInstanceState == null) {
            // активити запускается впервые
        } else {
            // активити уже создавалось
        }
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showSnackBar(item.getTitle().toString());
                item.setChecked(true); // выделение нажатого item
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {  // если нажата кнопка гамбургер
            mNavigationDrawer.openDrawer(GravityCompat.START); // то открыть навигационую панель
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Замена Toast (альтернатива)
     */
    private void showSnackBar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_img:
                /*showProgress();
                runWithDelay();*/
        }
    }

   /* private void runWithDelay() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Todo выполняем метод с задержкой в 5 секунд
               hideProgress();
            }
        }, 5000);

    }*/

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
