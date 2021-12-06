package com.skilldistillery.film.controllers;

import java.sql.SQLException;
import java.util.List;

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

//	@RequestMapping({"/", "home.do"})
//	public String home(Model model) {
//		model.addAttribute("TEST", "Hello, Spring MVC!");
//		return "home";
//	}

	@RequestMapping(path = "GetKeywordFilmData.do", params = "filmKeyword", method = RequestMethod.GET)
	public ModelAndView getFilmByKeyword(String filmKeyword) {
		ModelAndView mv = new ModelAndView();
		List<Film> film = filmDao.findFilmsByKeyword(filmKeyword);
		mv.addObject("films", film);
		mv.setViewName("result");
		return mv;

	}

	@RequestMapping(path = "GetFilmData.do", params = "filmId", method = RequestMethod.GET)
	public ModelAndView getFilmById(int filmId) {
		ModelAndView mv = new ModelAndView();

		Film film = filmDao.findFilmById(filmId);
		mv.addObject("film", film);
		mv.setViewName("result");
		return mv;

	}

	@RequestMapping(path = "NewFilmData.do", method = RequestMethod.POST)
	public ModelAndView createNewFilm(String title, String description, Integer languageId, String rating,
			Integer releaseYear, int length) {
		ModelAndView mv = new ModelAndView();
		Film film = new Film();
		film.setTitle(title);
		film.setDescription(description);
		film.setRating(rating);
		film.setLanguageId(languageId);
		film.setReleaseYear(releaseYear);
		film.setLength(length);
		filmDao.createFilm(film);
		mv.addObject("film", film);
		mv.setViewName("result");

		return mv;

	}

	@RequestMapping(path = "RemoveFilmData.do", method = RequestMethod.POST)
	public ModelAndView deleteOldFilm(int filmId) {
		ModelAndView mv = new ModelAndView();
		Film film = filmDao.findFilmById(filmId);
		if (film != null) {
			try {
				filmDao.deleteFilm(film);
				mv.addObject("message", "Film deleted: " + film.getTitle());
			} catch (SQLException e) {
				mv.addObject("message", "Cannot delete or update a parent row");
			}
			mv.setViewName("redirect");
			return mv;
		} 
			mv.setViewName("noFilm");
			return mv;
	}

	@RequestMapping(path = "UpdateFilmData.do", method = RequestMethod.POST)
	public ModelAndView updateNewFilm(Film film) {
		ModelAndView mv = new ModelAndView();
		film = filmDao.updateSpecificFilm(film);
		mv.addObject("film", film);
		mv.setViewName("redirect:GetFilmData.do?filmId=" + film.getId());

		return mv;

	}

	@RequestMapping(path = "UpdateFilmDataForm.do", method = RequestMethod.GET)
	public ModelAndView updateForm(int filmId) {
		ModelAndView mv = new ModelAndView();
		Film film = filmDao.findFilmById(filmId);

		mv.addObject("film", film);
		mv.setViewName("update");
		return mv;
	}
}
