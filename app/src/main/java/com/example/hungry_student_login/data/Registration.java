package com.example.hungry_student_login.data;

public class Registration {

    public static String id;

    public static String pwd;

    public String repwd;

    public static String email;

    public static int scode;

    public static String nickname;

    public static Integer auth;

    public Registration(String id, String pwd, String repwd, String email, int scode, String nickname, Integer auth) {
        this.id = id;
        this.pwd = pwd;
        this.repwd = repwd;
        this.email = email;
        this.scode = scode;
        this.nickname = nickname;
        this.auth = auth;
    }

    public String getId() { return id; }

    public String getPwd() { return pwd; }

    public String getRepwd() { return repwd; }

    public String getEmail() { return email; }

    public int getScode() { return scode; }

    public String getNickname() { return nickname; }

    public Integer getAuth() { return auth; }



}
