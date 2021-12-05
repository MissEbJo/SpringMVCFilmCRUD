package com.skilldistillery.film.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.data.FilmDAO;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {
	
	@Autowired
	private FilmDAO filmDao;
	
	public void setFilmDAO(FilmDAO filmDao) {
		this.filmDao = filmDao;
	}
	
	@RequestMapping({"/", "home.do"})
	public String home(Model model) {
		model.addAttribute("TEST", "Hello, Spring MVC!");
		return "home";
	}

	@RequestMapping(path="GetFilmData.do", params="filmId", method =RequestMethod.GET)
	public ModelAndView getFilmById(int filmId) {
		ModelAndView mv = new ModelAndView();
		
		Film film = filmDao.findFilmById(filmId);
		mv.addObject("film", film);
		mv.setViewName("result");
		return mv;
		
	}
	
	@RequestMapping(path="NewFilmData.do", method= RequestMethod.POST)
		public ModelAndView createNewFilm(String title, String description, Integer languageId, String rating, String releaseYear, int length){
		ModelAndView mv = new ModelAndView();
		Film film = new Film();
		film.setTitle(title);
		film.setDescription(description);
		film.setRating(rating);
		film.setLanguageId(languageId);
		film.setReleaseYear(releaseYear);
		film.setLength(length);
//		System.out.println(film);
		filmDao.createFilm(film);
		mv.addObject("film", film);
		mv.setViewName("result");
		
		return mv;
		
	}
	@RequestMapping(path="RemoveFilmData.do", method=RequestMethod.POST)
	public ModelAndView deleteOldFilm (int filmId) {
		ModelAndView mv = new ModelAndView();
		Film film = filmDao.findFilmById(filmId);
		filmDao.deleteFilm(film);
		//this is not functioning properly not sure why
//		mv.addObject("film", film);
		mv.setViewName("result");
		return mv;
	}
	
}
