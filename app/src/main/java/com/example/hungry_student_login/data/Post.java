package com.example.hungry_student_login.data;

public class Post {
    int pnum;
    String nickname;
    String ptitle;
    String pcontent;
    String ptime;
    int scode;

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }

    public String getPcontent() {
        return pcontent;
    }

    public void setPcontent(String pcontent) {
        this.pcontent = pcontent;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public int getScode() {
        return scode;
    }

    public void setScode(int scode) {
        this.scode = scode;
    }

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "pnum=" + pnum +
                ", nickname='" + nickname + '\'' +
                ", ptitle='" + ptitle + '\'' +
                ", pcontent='" + pcontent + '\'' +
                ", ptime='" + ptime + '\'' +
                ", scode=" + scode +
                '}';
    }
}
