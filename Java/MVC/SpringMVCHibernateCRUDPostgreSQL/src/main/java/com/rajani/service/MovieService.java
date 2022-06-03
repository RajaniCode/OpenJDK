package com.rajani.service;
 
import java.util.List;
 
import com.rajani.model.Movie;
 
public interface MovieService {
     
    public void addMovie(Movie movi);
 
    public List<Movie> getAllMovies();
 
    public void deleteMovie(Integer movieId);
 
    public Movie getMovie(int movieid);
 
    public Movie updateMovie(Movie movi);
}
