package com.rajani.dao;
 
import java.util.List;
 
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
import com.rajani.model.Movie;
 
@Repository
public class MovieDAOImpl implements MovieDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public void addMovie(Movie movi) {
        sessionFactory.getCurrentSession().saveOrUpdate(movi);
 
    }
 
    @SuppressWarnings("unchecked")
    public List<Movie> getAllMovies() {
 
        return sessionFactory.getCurrentSession().createQuery("from Movie")
                .list();
    }
 
    @Override
    public void deleteMovie(Integer movieId) {
        Movie movi = (Movie) sessionFactory.getCurrentSession().load(
                Movie.class, movieId);
        if (null != movi) {
            this.sessionFactory.getCurrentSession().delete(movi);
        }
 
    }
 
    public Movie getMovie(int movieId) {
        return (Movie) sessionFactory.getCurrentSession().get(
                Movie.class, movieId);
    }
 
    @Override
    public Movie updateMovie(Movie movi) {
        sessionFactory.getCurrentSession().update(movi);
        return movi;
    }
 
}