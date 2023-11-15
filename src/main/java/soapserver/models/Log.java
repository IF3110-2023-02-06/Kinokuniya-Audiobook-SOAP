package soapserver.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "logs")
@Getter
@Setter
public class Log implements Serializable {
  @Id
  @Basic
  private Timestamp time;

  @Id
  private String origin;

  private String method;

  private String description;
}
