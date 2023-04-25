package com.esmt.sn.schoolmanagement.models;

import com.esmt.sn.schoolmanagement.models.enums.TypePrivilege;
import jakarta.persistence.*;

@Entity(name = "t_privilege")
public class Privilege  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_privilege")
    protected int id;
	@Enumerated(EnumType.STRING)
	private TypePrivilege name;
	private String description;
	private String category;


	public Privilege() {
	}

	public Privilege(TypePrivilege name) {
		this.name = name;
	}

	public Privilege(String category, TypePrivilege name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TypePrivilege getName() {
		return name;
	}

	public void setName(TypePrivilege name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return name.toString();
	}
}
