package com.example.calculator;

import android.app.Activity;
import android.content.Intent;

import com.example.calculator.About;

public class ThemeManager {

    // Идентификаторы стилей
    final static int THEME_1 = 1;
    final static int THEME_2 = 2;

    // по умолчанию включена первая тема
    private static int mTheme = THEME_1;

    /**
     *
     * @param activity  - экран, который вызывает переключения
     * @param theme     - тема, на которую нужно переключиться
     */
    static void changeTheme(Activity activity, int theme){
        mTheme = theme;

        activity.finish();

        // закроем окно about
        Intent intent = new Intent(activity.getApplicationContext(), About.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);

        Intent intentMain = new Intent(activity.getApplicationContext(), MainActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intentMain);
    }

    static void setTheme(Activity activity){
        switch(mTheme){
            case THEME_1:
            {
                activity.setTheme(R.style.my_theme);
                break;
            }
            case THEME_2:
            {
                activity.setTheme(R.style.dark_theme);
                break;
            }
        }
    }

}
