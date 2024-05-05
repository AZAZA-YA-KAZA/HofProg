package com.example.hofprog.model;
public class whoi {
    private Integer id;
    private Integer man;
    private Integer prog;
    private String nick;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public whoi(Integer id, String nick, Integer man, Integer prog) {
        this.id = id;
        this.nick = nick;
        this.man = man;
        this.prog = prog;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMan() {
        return man;
    }
    public void setMan(Integer man) {
        this.man = man;
    }

    public Integer getProg() {
        return prog;
    }

    public void setProg(Integer prog) {
        this.prog = prog;
    }
}
