package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // кнопки цифр
    Button mBtn0;
    Button mBtn1;
    Button mBtn2;
    Button mBtn3;
    Button mBtn4;
    Button mBtn5;
    Button mBtn6;
    Button mBtn7;
    Button mBtn8;
    Button mBtn9;

    TextView mDisplay;
    TextView mSecondOrderDisplay;
    TextView mThreeOrderDisplay;
    TextView mSign;

    // кнопки операторов
    Button mBtnComma;
    Button mBtnResult;
    Button mBtnMinus;
    Button mBtnPlus;
    Button mBthMultiplication;
    Button mBtnDivision;
    Button mBtnBack;
    Button mBtnClear;

    Button mBtnPlusMinus;

    // будет хранить число, которое вводил пользователь до нажатия оператора
    private float mValue = 0;
    // будет хранить выбранный нами оператор
    private String mOperator = "";
    // результат вычислений
    float result = 0;

    /**
     * @param menu - объект меню
     * @return успешность подключения
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     *  Копирование результатов вычисления в буфер обмена
     */
    private void copy(){
        // Получение менеджера
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        if(clipboard != null){
            // создаем вырезанные данные из текста
            ClipData clip = ClipData.newPlainText("", mDisplay.getText());

            // устанавливаем данные в буфер обмена
            clipboard.setPrimaryClip(clip);
        }
    }

    /**
     *  Вставка значения из буфера обмена
     */
    private void paste(){
        // Получение менеджера
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        // убеждаем, что менеджер доступен
        if(clipboard != null){

            // проверка, что в буфере обмена есть текст
            if(clipboard.hasPrimaryClip()
                    && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){

                // получаем данные из буфера
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

                String pasteData = item.getText().toString();

                if(isNumeric(pasteData))
                    mDisplay.setText(pasteData);
            }
        }
    }
    /**
     * Открытие экрана Settings
     */
    private void startSettings(){
        Intent activity = new Intent(getApplicationContext(), Settings.class);
        startActivity(activity);
    }

    /**
     * Открытие экрана About
     */
    private void about(){
        Intent activity = new Intent(getApplicationContext(), About.class);
        startActivity(activity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.copy:
            {
                copy();
                return true;
            }
            case R.id.paste:
            {
                paste();
                return true;
            }
            case R.id.action_settings:
            {
                startSettings();
                return true;
            }
            case R.id.about:
            {
                about();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);
        setContentView(R.layout.activity_main);


        mBtn0 = findViewById(R.id.btnNumber0);
        mBtn1 = findViewById(R.id.btnNumber1);
        mBtn2 = findViewById(R.id.btnNumber2);
        mBtn3 = findViewById(R.id.btnNumber3);
        mBtn4 = findViewById(R.id.btnNumber4);
        mBtn5 = findViewById(R.id.btnNumber5);
        mBtn6 = findViewById(R.id.btnNumber6);
        mBtn7 = findViewById(R.id.btnNumber7);
        mBtn8 = findViewById(R.id.btnNumber8);
        mBtn9 = findViewById(R.id.btnNumber9);

        mDisplay = findViewById(R.id.textView);
        mSecondOrderDisplay = findViewById(R.id.textView2);
        mThreeOrderDisplay = findViewById(R.id.textView3);
        mSign = findViewById(R.id.textView5);

        mBtnComma = findViewById(R.id.btnComma);
        mBtnResult = findViewById(R.id.btnResult);
        mBtnMinus = findViewById(R.id.btnMinus);
        mBtnPlus = findViewById(R.id.btnPlus);
        mBthMultiplication = findViewById(R.id.bthMultiplication);
        mBtnDivision = findViewById(R.id.btnDivision);
        mBtnBack = findViewById(R.id.btnBack);
        mBtnClear = findViewById(R.id.btnClear);
        mBtnPlusMinus = findViewById(R.id.btnPlusMinus);

        mBtn0.setOnClickListener(numberListener);
        mBtn1.setOnClickListener(numberListener);
        mBtn2.setOnClickListener(numberListener);
        mBtn3.setOnClickListener(numberListener);
        mBtn4.setOnClickListener(numberListener);
        mBtn5.setOnClickListener(numberListener);
        mBtn6.setOnClickListener(numberListener);
        mBtn7.setOnClickListener(numberListener);
        mBtn8.setOnClickListener(numberListener);
        mBtn9.setOnClickListener(numberListener);

        mBtnResult.setOnClickListener(resultListener);

        mBtnPlus.setOnClickListener(operatorListener);
        mBtnMinus.setOnClickListener(operatorListener);
        mBthMultiplication.setOnClickListener(operatorListener);
        mBtnDivision.setOnClickListener(operatorListener);

        mBtnBack.setOnClickListener(clearListener);
        mBtnClear.setOnClickListener(clearListener);
        mBtnPlusMinus.setOnClickListener(plusMinusListener);

        mBtnComma.setOnClickListener(commaListener);

    }

    View.OnClickListener clearListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onClearClick(view);
        }

        private void onClearClick(View operatorButton){
            String cleanupOperator = ((Button) operatorButton).getText().toString();
            String display;

            if(cleanupOperator.equals("C")){
                mDisplay.setText("0");
                mSecondOrderDisplay.setText("");
                mThreeOrderDisplay.setText("");
                mSign.setText("");
            }
            else if(cleanupOperator.equals("B")){
                display = mDisplay.getText().toString();

                if(display.contains("-") && display.length() == 2)
                    mDisplay.setText("0");

                else if(display.length() < 2 && (!display.equals("0") || !display.equals("-"))){
                    mDisplay.setText("0");
                }
                else if(display != null && display.length() > 0 && !display.equals("0")){
                    display = display.substring(0, display.length() - 1);
                    mDisplay.setText(display);
                }
            }
        }
    };

    View.OnClickListener numberListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onNumberClick(view);
        }

        /**
         * @param button - Обработка нажатия
         */
        private void onNumberClick(View button) {
            String number = ((Button) button).getText().toString();
            String display = mDisplay.getText().toString();

            if (display.equals("0"))
                display = number;
            else
                display += number;
            mDisplay.setText(display);
        }

    };

    View.OnClickListener operatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onOperatorClick(view);
        }

        @SuppressLint("SetTextI18n")
        private void onOperatorClick(View operatorButton){
            String operator = ((Button) operatorButton).getText().toString();
            mOperator = operator;

            String display = mDisplay.getText().toString();
            if(result != 0)
                mThreeOrderDisplay.setText(Float.toString(result));
            mSecondOrderDisplay.setText(display);

            mValue = Float.parseFloat(display);

            mSign.setText(operator);
            mDisplay.setText("0");
        }
    };

    View.OnClickListener resultListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onResultClick(view);
        }

        @SuppressLint("SetTextI18n")
        private void onResultClick(View resultListener){
            String display = mDisplay.getText().toString();
            float value = Float.parseFloat(display);

            mSecondOrderDisplay.setText(display);
            mThreeOrderDisplay.setText(Float.toString(mValue));

            result = value;

            switch(mOperator){
                case "+":
                {
                    result = mValue + value;
                    break;
                }
                case "-":
                {
                    result = mValue - value;
                    break;
                }
                case "*":
                {
                    result = mValue * value;
                    break;
                }
                case "/":
                {
                    result = mValue / value;
                    break;
                }
            }

            DecimalFormat format = new DecimalFormat("0.##");
            format.setRoundingMode(RoundingMode.DOWN);
            String resultText = format.format(result);

            mDisplay.setText(resultText);

            mValue = result;
            mOperator = "";
        }
    };

    public static boolean contains(String str, String substr){
        return str.contains(substr);
    }

    View.OnClickListener commaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onCommaClick(view);
        }

        private void onCommaClick(View button){
//            String line = ((Button) button).getText().toString();
            String display = mDisplay.getText().toString();

            if(display.length() > 0 && !contains(display, ".")){
                display += ".";
                mDisplay.setText(display);
            }
        }
    };

    View.OnClickListener plusMinusListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onPlusMinusClick(view);
        }

        private void onPlusMinusClick(View button){
            String display = mDisplay.getText().toString();

            if(!display.contains("-") && !display.equals("0") && !display.equals("0.") )
                display = "-" + display;
            else if(display.contains("-"))
                display = display.replace("-", "");

            mDisplay.setText(display);
        }
    };

    /**
     * @param data - данные на проверку
     * @return успешность проверка
     */
    private static boolean isNumeric(String data){
        if(data == null)
            return false;

        try{
            Double.parseDouble(data);
        }
        catch(NumberFormatException ex){
            return false;
        }
        return true;
    }

}
