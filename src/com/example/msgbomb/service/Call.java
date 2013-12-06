package com.example.msgbomb.service;

import com.example.msgbomb.vo.UrlVo;

/**
 * User: tangbin
 * Date: 13-12-5
 * Time: 下午4:03
 */
public class Call {
    public void phone(String num) {
        UrlVo url = new UrlVo();

        String [] getList = url.getMyUrls();

        for (int i = 0; i < getList.length; i ++) {
            String u = getList[i];
            String t = u.replace("{tel}", num);
            String s = HttpService.sendGet(t);
            System.out.print(s);
        }

        String [] postList = url.getMyUrls();

        for (int i = 0; i < postList.length; i ++) {
            String u = postList[i];
            String t = u.replace("{tel}", num);
            String [] args = t.split("||");

            if (args.length < 2) {
                args[1] = "";
            }

            String s = HttpService.sendPost(args[0], args[1]);
            System.out.print(s);
        }

    }
}
