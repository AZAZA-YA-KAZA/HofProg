package com.example.hofprog.model;
public class proger {
    private Integer id;
    private String nick;
    private String name;
    private String fam;
    private String pochta;
    private String password;
    private String tel;
    private String exam;
    private String badex;

    public proger(Integer id, String nick, String name, String fam, String pochta, String password, String tel, String exam, String badex) {
        this.id = id;
        this.nick = nick;
        this.name = name;
        this.fam = fam;
        this.pochta = pochta;
        this.password = password;
        this.tel = tel;
        this.exam = exam;
        this.badex = badex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getPochta() {
        return pochta;
    }

    public void setPochta(String pochta) {
        this.pochta = pochta;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(Integer[] exam) {
        this.exam = " "+tostr(exam);
    }

    public String getBadex() {
        return badex;
    }

    public void setBadex(Integer[] badex) {
        this.badex = " "+tostr(badex);
    }
    public String tostr(Integer[] mas){
        String q = "";
        if (mas == null) return "";
        for (int i: mas){q+= i+"";}
        return q;
    }
    public Integer[] toint(String str) {
        String[] strArr = str.split(" ");
        Integer[] intArr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            intArr[i] = Integer.parseInt(strArr[i]);
        }
        return intArr;
    }

    @Override
    public String toString() {
        return "proger{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", name='" + name + '\'' +
                ", fam='" + fam + '\'' +
                ", pochta='" + pochta + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", exam='" + exam + '\'' +
                ", badex='" + badex + '\'' +
                '}';
    }
}
