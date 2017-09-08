package com.softdesign.devintensive.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.softdesign.devintensive.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mCallImg;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mCallImg = (ImageView) findViewById(R.id.call_img);
        mCallImg.setOnClickListener(this);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        if (savedInstanceState == null) {
            // активити запускается впервые
        } else {
            // активити уже создавалось
        }
        setupToolbar();

    }

    /**
     * Замена Toast
     */
    private void showSnackBar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG);
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
