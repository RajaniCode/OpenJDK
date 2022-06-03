package com.rajani.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.rajani.model.Movie;
import com.rajani.service.MovieService;

@Controller
public class MovieController {

    private static final Logger logger = Logger
		.getLogger(MovieController.class);

    public MovieController() {
		System.out.println("MovieController()");
	}

    @Autowired
    private MovieService movieService;


    @RequestMapping(value = {"", "/", "home"})
    public ModelAndView listMovie(ModelAndView model) throws IOException {
    	List<Movie> listMovie = movieService.getAllMovies();
    	model.addObject("listMovie", listMovie);
    	model.setViewName("home");
    	return model;
    }

    @RequestMapping(value = "/newMovie", method = RequestMethod.GET)
    public ModelAndView newContact(ModelAndView model) {
    	Movie movi = new Movie();
    	model.addObject("movie", movi);
    	model.setViewName("movie-form");
    	return model;
    }

    @RequestMapping(value = "/saveMovie", method = RequestMethod.POST)
    public ModelAndView saveMovie(@ModelAttribute Movie movi) {
        if (movi.getId() == 0) { // if movie id is 0 then creating the
            // movie other updating the movie
            movieService.addMovie(movi);
    	} else {
            movieService.updateMovie(movi);
    	}
    	return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/deleteMovie", method = RequestMethod.GET)
    public ModelAndView deleteMovie(HttpServletRequest request) {
        int movieId = Integer.parseInt(request.getParameter("id"));
        movieService.deleteMovie(movieId);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/editMovie", method = RequestMethod.GET)
    public ModelAndView editContact(HttpServletRequest request) {
        int movieId = Integer.parseInt(request.getParameter("id"));
        Movie movi = movieService.getMovie(movieId);
        ModelAndView model = new ModelAndView("movie-form");
        model.addObject("movie", movi);

    	return model;
    }

}