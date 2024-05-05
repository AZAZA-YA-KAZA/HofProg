package com.example.hofprog.model;

public class manage {
    private Integer id;
    private String nick;
    private String name;
    private String fam;
    private String pochta;
    private String password;
    private String tel;
    private String progr_id;
    private String new_task_id;
    private String on_prov_task_id;

    public manage(Integer id, String nick, String name, String fam, String pochta, String password, String tel, String progr_id, String new_task_id, String on_prov_task_id) {
        this.id = id;
        this.nick = nick;
        this.name = name;
        this.fam = fam;
        this.pochta = pochta;
        this.password = password;
        this.tel = tel;
        this.progr_id = progr_id;
        this.new_task_id = new_task_id;
        this.on_prov_task_id = on_prov_task_id;
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

    public String getProgr_id() {
        return progr_id;
    }

    public void setProgr_id(String progr_id) {
        this.progr_id = progr_id;
    }

    public String getNew_task_id() {
        return new_task_id;
    }

    public void setNew_task_id(Integer[] new_task_id) {
        this.new_task_id = " "+tostr(new_task_id);
    }

    public String getOn_prov_task_id() {
        return on_prov_task_id;
    }

    public void setOn_prov_task_id(Integer[] on_prov_task_id) {
        this.on_prov_task_id = " "+tostr(on_prov_task_id);
    }

    public String findbyid(Integer id){ return this.password;}

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
        return "manage{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", name='" + name + '\'' +
                ", fam='" + fam + '\'' +
                ", pochta='" + pochta + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", progr_id='" + progr_id + '\'' +
                ", new_task_id='" + new_task_id + '\'' +
                ", on_prov_task_id='" + on_prov_task_id + '\'' +
                '}';
    }
}