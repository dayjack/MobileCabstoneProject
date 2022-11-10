package com.example.hungry_student_login.data;

public class ReviewData {

    int vcode;
    int restaurant_id;
    String vtime;
    String vcontent;
    float rate;
    String nickname;

    public int getVcode() {
        return vcode;
    }

    public void setVcode(int vcode) {
        this.vcode = vcode;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getVtime() {
        return vtime;
    }

    public void setVtime(String vtime) {
        this.vtime = vtime;
    }

    public String getVcontent() {
        return vcontent;
    }

    public void setVcontent(String vcontent) {
        this.vcontent = vcontent;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ReviewData(int vcode, int restaurant_id, String vtime, String vcontent, float rate, String nickname) {
        this.vcode = vcode;
        this.restaurant_id = restaurant_id;
        this.vtime = vtime;
        this.vcontent = vcontent;
        this.rate = rate;
        this.nickname = nickname;
    }

    public ReviewData() {

    }
}
