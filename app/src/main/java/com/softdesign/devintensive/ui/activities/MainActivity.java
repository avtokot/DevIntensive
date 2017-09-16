package com.softdesign.devintensive.ui.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {


    private int mCurrentEditMode = 0;
    private RelativeLayout mProfilePlaceholder;
    private ImageView mCallImg;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFloatingActionButton;
    private EditText mUserPhone, mUserEmail, mUserVk, mUserGit, mUserAccount;
    private DataManager mDataManager;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout.LayoutParams mAppBarParams = null;
    private AppBarLayout mAppBarLayout;

    private List<EditText> mUserInfoViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataManager = DataManager.getInstance();
        mProfilePlaceholder = (RelativeLayout) findViewById(R.id.profile_placeholder);
        mProfilePlaceholder.setOnClickListener(this);

        mUserEmail = (EditText) findViewById(R.id.email_et);
        mUserPhone = (EditText) findViewById(R.id.phone_et);
        mUserVk = (EditText) findViewById(R.id.vk_et);
        mUserGit = (EditText) findViewById(R.id.git_et);
        mUserAccount = (EditText) findViewById(R.id.account_et);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);

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

            case R.id.profile_placeholder:
                // Todo Откуда будет идти загрузка изображения
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
                break;
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
        mAppBarParams = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams(); // инициализация (передача параметров)
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
                showProfilePlaceholder();
                lockToolbar();
                mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT); // делает текст в Toolbare прозрачным (Имя, Фамилия)
            }
        } else {
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                mFloatingActionButton.setImageResource(R.drawable.ic_check_black_24dp);
                saveUserInfoValue(); // метод для сохранения данных
                hideProfilePlaceholder();
                unlockToolbar();
                mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white)); // возвращаем системный цвет тексту (Имя, Фамилия)
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
    } // сохранение данных перед пересозданием самой активити♥

    private void loadPhotoFromGallery() {

    } // загрузка фото с галереи

    private void loadPhotoFromCamera() {

    } // загрузка фото с камеры

    private void hideProfilePlaceholder() {
        mProfilePlaceholder.setVisibility(View.GONE);
    } // скрытие режима редактирования фото

    private void showProfilePlaceholder() {
        mProfilePlaceholder.setVisibility(View.VISIBLE);
    } // отображение режима редактирования фото

    private void lockToolbar() {
        mAppBarLayout.setExpanded(true, true);  // метод указывает на анимацию (плавный переход)
        mAppBarParams.setScrollFlags(0);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    } // Toolbar заблокирован

    private void unlockToolbar() {
        mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    } // Toolbar разблокирован

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItems = {getString(R.string.user_profile_dialog_gallery), getString(R.string.user_profile_dialog_camera), getString(R.string.user_profile_dialog_cancel)};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.user_profile_dialog_title);
                builder.setItems(selectItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choiceItem) {
                        switch (choiceItem) {
                            case 0:
                                // TODO: 15.09.2017
                                showSnackBar("Загрузить из галереи");
                                break;
                            case 1:
                                // TODO: 15.09.2017
                                showSnackBar("Сделать фото");
                                break;
                            case 2:
                                // TODO: 15.09.2017
                                showSnackBar("Отмена");
                                dialog.cancel();
                                break;
                        }
                    }
                });
                return builder.create();
        }
        return null;
    } // запуск диалогового окна
}
