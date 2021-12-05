package com.skilldistillery.film.data;

import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Category;
import com.skilldistillery.film.entities.Film;

public interface FilmDAO {
	public Film findFilmById(int filmId);
	  
	  public Actor findActorById(int actorId);
	  
	  public List<Actor> findActorsByFilmId(int filmId);
	  
	  public List<Film> findFilmsByKeyword(String theWordToSearchFor);
	  
	  public Film createFilm(Film newFilm);
	  
	  public boolean deleteFilm(Film film);
	  
	//  public boolean updateFilm(Film film);

	public  Film updateDescriptionOfSpecificFilm(Film film, int filmId);
	public Category findCategoryById(int filmId);
}
