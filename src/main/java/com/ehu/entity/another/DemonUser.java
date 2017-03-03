package com.ehu.entity.another;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the demon_user database table.
 * 
 */
@Entity
@Table(name="demon_user")
@NamedQuery(name="DemonUser.findAll", query="SELECT d FROM DemonUser d")
public class DemonUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	public DemonUser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}