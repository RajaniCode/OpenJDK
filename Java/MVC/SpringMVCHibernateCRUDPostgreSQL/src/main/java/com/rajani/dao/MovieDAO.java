package com.rajani.dao;
 
import java.util.List;
import com.rajani.model.Movie;
 
public interface MovieDAO {
 
    public void addMovie(Movie movi);
 
    public List<Movie> getAllMovies();
 
    public void deleteMovie(Integer movieId);
 
    public Movie updateMovie(Movie movi);
 
    public Movie getMovie(int movieId);
}