package com.skilldistillery.film.entities;

import java.util.List;
import java.util.Objects;

public class Category {
	private int id;
	private String name; 
//	private List<Film> films;
	
	public Category() {
	}
	

	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public List<Film> getFilms() {
//		return films;
//	}
//
//	public void setFilms(List<Film> films) {
//		this.films = films;
//	}


	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return id == other.id && Objects.equals(name, other.name);
	}


	@Override
	public String toString() {
		return "Category id=" + id + ", name=" + name ;
	}
	
	
}
