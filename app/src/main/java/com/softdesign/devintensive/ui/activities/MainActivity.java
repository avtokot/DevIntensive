package com.softdesign.devintensive.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {


    private int mCurrentEditMode = 0;
    private ImageView mCallImg;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFloatingActionButton;
    private EditText mUserPhone, mUserEmail, mUserVk, mUserGit, mUserAccount;
    private DataManager mDataManager;


    private List<EditText> mUserInfoViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataManager = DataManager.getInstance();

        mUserEmail = (EditText) findViewById(R.id.email_et);
        mUserPhone = (EditText) findViewById(R.id.phone_et);
        mUserVk = (EditText) findViewById(R.id.vk_et);
        mUserGit = (EditText) findViewById(R.id.git_et);
        mUserAccount = (EditText) findViewById(R.id.account_et);

        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserEmail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserAccount);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_edit);
        mFloatingActionButton.setOnClickListener(this);

        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mCallImg = (ImageView) findViewById(R.id.call_img);
        mCallImg.setOnClickListener(this);  // повесил обработчик нажатий на FloatingActionBar

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);

        setupToolbar();
        setupDrawer();
        loadUserInfoValue(); // метод загрузки данных при создании активити

        List<String> test = mDataManager.getPreferencesManager().loadUserProfileData();

        if (savedInstanceState == null) {
            // активити запускается впервые
        } else {
            // активити уже создавалось
            mCurrentEditMode = savedInstanceState.getInt("EDIT_MODE_KEY", 0);
            changeEditMode(mCurrentEditMode);
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
                break;
            case R.id.fab_edit:
                if (mCurrentEditMode == 0) {
                    showSnackBar("режим редактирования включен");
                    changeEditMode(1);
                    mCurrentEditMode = 1;
                } else {
                    showSnackBar("режим просмотра включен");
                    changeEditMode(0);
                    mCurrentEditMode = 0;
                }
                break;
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

    /**
     * переключает режим редактирования
     * если currentEditMode равен 1 то режим редактирования, если 0 то режим просмотра
     */
    private void changeEditMode(int currentEditMode) {
        if (currentEditMode == 1) {
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
                mFloatingActionButton.setImageResource(R.drawable.ic_mode_edit_black_24dp);
            }
        } else {
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                mFloatingActionButton.setImageResource(R.drawable.ic_check_black_24dp);
                saveUserInfoValue(); // метод для сохранения данных
            }
        }
    }

    private void loadUserInfoValue() {
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue() {
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userData);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
        super.onSaveInstanceState(outState);
    }
}
