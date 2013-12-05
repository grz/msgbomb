package com.example.msgbomb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.msgbomb.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyActivity extends Activity {
    private EditText e_phoneNumber;
    private ImageButton b_attack;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        e_phoneNumber = (EditText) findViewById(R.id.e_phoneNumber);
        b_attack = (ImageButton) findViewById(R.id.b_attack);
        b_attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuitedNum = validateNum(e_phoneNumber.getText().toString());
                if(isSuitedNum){
                    attack();
                }else{

                }
            }
        });
    }

    public void attack(){

        //先执行后台逻辑再跑动画
        startAnimation(b_attack, R.anim.bombing);
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

    public void startAnimation(View v,int id){
        Animation bombing = AnimationUtils.loadAnimation(this,id);
        if(bombing != null){
            bombing.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    bombToast("fighting...",false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    bombToast("end",false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    bombToast("Repeat",false);
                }
            });
        }
        v.startAnimation(bombing);

    }
}
