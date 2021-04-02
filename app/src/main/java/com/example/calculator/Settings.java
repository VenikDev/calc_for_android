package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    Settings context;
    Button buttonTheme1;
    Button buttonTheme2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);

        setContentView(R.layout.activity_settings);

        buttonTheme1 = findViewById(R.id.buttonTheme1);
        buttonTheme2 = findViewById(R.id.buttonTheme2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      // показать копку назад

        buttonTheme1.setOnClickListener(firstThemeListener);
        buttonTheme2.setOnClickListener(secondThemeListener);

        context = this;
    }

    View.OnClickListener firstThemeListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            ThemeManager.changeTheme(context, ThemeManager.THEME_1);
        }
    };

    View.OnClickListener secondThemeListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            ThemeManager.changeTheme(context, ThemeManager.THEME_2);
        }
    };

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}