package com.example.msgbomb.service;

/**
 * User: tangbin
 * Date: 13-12-5
 * Time: 下午4:03
 */
public class Call {
    void phone(String num) {
        String s = HttpService.sendGet("https://www.qianwang365.com/uc/ajax/obtainSecurityCode4Regist.html", "username=" + num);
        System.out.print(s);
    }
}
