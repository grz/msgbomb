package com.example.msgbomb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyActivity extends Activity {
    private EditText e_phoneNumber;
    private Button b_attack;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        e_phoneNumber = (EditText) findViewById(R.id.e_phoneNumber);
        b_attack = (Button) findViewById(R.id.b_attack);
        b_attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attack(e_phoneNumber.getText().toString());
            }
        });
    }

    public void attack(String num){
        boolean isSuitedNum = validateNum(num);
        if(isSuitedNum){
            bombToast("等bingzi就能公鸡了!",false);
        }else{

        }
    }

    public boolean validateNum(String num){
        if(num.length() == 0){
            bombToast("The Attacked Num can not be null!",false);
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(num);
        boolean isMatch = m.matches();
        if(!isMatch){
            bombToast("please input the num in right pattern!",false);
        }
        return isMatch;
    }

    public void bombToast(String msg,boolean isLong){
        if(isLong){
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }

    }
}
