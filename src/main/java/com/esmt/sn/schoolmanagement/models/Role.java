package com.esmt.sn.schoolmanagement.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity(name="t_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_role")
    protected int id;
	private String name;
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	protected List<Privilege> privileges = new ArrayList<>();


	public Role() {
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String toString() {
		return "Role{" +
				"name='" + name + '\'' +
				'}';
	}
}
