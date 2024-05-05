package com.example.hofprog.model;
public class task {
    private Integer id;
    private Integer stat;
    private String nam;
    private String opis;
    private String sroki;
    private String nick;

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public void setSroki(String sroki) {
        this.sroki = sroki;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public task(Integer id, String nick, Integer state, String nam, String opis, String sroki) {
        this.id = id;
        this.nick = nick;
        this.stat = state;
        this.nam = nam;
        this.opis = opis;
        this.sroki = sroki;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return stat;
    }

    public void setState(Integer state) {
        this.stat = state;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSroki() {
        return sroki;
    }

    public void setSroki(Integer[] sroki) {
        this.sroki = " "+tostr(sroki);
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
}
