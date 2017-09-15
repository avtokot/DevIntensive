package com.softdesign.devintensive.ui.activities;

import android.content.Intent;
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
                switch (item.getItemId()) {
                    case R.id.team_menu:
                        showSnackBar("Выбрана командная разработка");
                        break;
                    case R.id.user_profile_menu:
                        showSnackBar("Выбран мой профиль");
                        break;
                }
                item.setChecked(true); // метод выделения нажатого item
                mNavigationDrawer.closeDrawer(GravityCompat.START); // закрытие Drawer
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNavigationDrawer.openDrawer(GravityCompat.START); // то открыть навигационую панель
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showSnackBar(String message) {  // замена Toast (альтернатива)
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    } // альтернатива Toast является SnackBar

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.email_img:
                //Todo реализовать отправку письма
                break;
            case R.id.git_img:
                //Todo реализовать чтение Git репозитория
                break;
            case R.id.vk_img:
                //Todo реализовать переход на страницу в Vk
                break;
            case R.id.call_img:
                //Todo реализовать вызов звонка
                break;
            case R.id.fab_edit:
                if (mCurrentEditMode == 0) {
                    showSnackBar("Режим редактирования включен");
                    changeEditMode(1);
                    mCurrentEditMode = 1;
                } else {
                    showSnackBar("Режим просмотра включен");
                    changeEditMode(0);
                    mCurrentEditMode = 0;
                }
                break;
        }
    } // обработчик нажатий на view элементы

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
    } // режим редактирования

    private void loadUserInfoValue() {
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    } // загрузка данных из SharedPrefences

    private void saveUserInfoValue() {
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userData);
    } // сохранение данных в SharedPrefences

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
        super.onSaveInstanceState(outState);
    } // сохранение данных перед пересозданием самой активити

    private void loadPhotoFromGallery() {

    } // загрузка фото с галереи

    private void loadPhotoFromCamera() {

    } // загрузка фото с камеры
}
