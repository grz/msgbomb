package com.example.msgbomb.vo;


import android.app.Activity;
import android.content.res.Resources;
import com.example.msgbomb.R;

/**
 * Created by mangosmwang on 13-12-5.
 */
public class UrlVo extends Activity{
    public String[] getMyUrls(){
       return getResources().getStringArray(R.array.get);
    }

    public String[] postMyUrls(){
        return getResources().getStringArray(R.array.post);
    }
}
