package com.fernandoog.model;

import javax.persistence.*;

@Entity
@Table(name = "scripts")
public class Script {

    @Column(name = "code")
    private String code;

    @Column(name = "result")
    private String result;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Script() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
