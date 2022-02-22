package com.fernandoog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scripts")
public class Script {

  @Column(name = "code")
  private String code;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  public Script() {
  }

  public Script(long id, String code) {
    super();
    this.id = id;
    this.code = code;
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
}
