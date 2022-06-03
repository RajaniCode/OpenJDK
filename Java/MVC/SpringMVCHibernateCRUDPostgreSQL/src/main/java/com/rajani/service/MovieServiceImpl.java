package com.rajani.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rajani.dao.MovieDAO;
import com.rajani.model.Movie;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDAO movieDAO;

    @Override
    @Transactional
    public void addMovie(Movie movi) {
	movieDAO.addMovie(movi);
    }

    @Override
    @Transactional
    public List<Movie> getAllMovies() {
	return movieDAO.getAllMovies();
    }

    @Override
    @Transactional
    public void deleteMovie(Integer movieId) {
	movieDAO.deleteMovie(movieId);
    }

    public Movie getMovie(int movieId) {
	return movieDAO.getMovie(movieId);
    }

    public Movie updateMovie(Movie movi) {
	return movieDAO.updateMovie(movi);
    }

    public void setMovieDAO(MovieDAO movieDAO) {
	this.movieDAO = movieDAO;
    }
}
