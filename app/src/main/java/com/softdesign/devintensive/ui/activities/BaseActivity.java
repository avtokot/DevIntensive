package com.softdesign.devintensive.ui.activities;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.softdesign.devintensive.R;

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog mProgressDialog;

    // показ загрузки
    public void showProgress() {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.custom_dialog);
            mProgressDialog.setCancelable(false); // метод не позволяет закрытие при помощи клавиши BACK
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // задали окну прозрачный цвет фона
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        } else {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
    }

    // закрытие загрузки
    public void hideProgress() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }
        }
    }

    public void showError(String message, Exception error) { //Todo требуется дальнейшая реализация метода
        showToast(message);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
