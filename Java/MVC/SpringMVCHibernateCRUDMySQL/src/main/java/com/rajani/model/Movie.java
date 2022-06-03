package com.rajani.model;
 
import java.io.Serializable;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
import java.sql.Date;

@Entity
@Table(name = "Movie")
public class Movie implements Serializable {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_date", nullable = false)
    private java.sql.Date release_date;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "rating", nullable = false)
    private String rating;

    public int getId() {
    	return id;
    }

    public void setId(int id) {
    	this.id = id;
    }

    public String getTitle() {
    	return title;
    }

    public void setTitle(String title) {
    	this.title = title;
    }

    public java.sql.Date getRelease_date() {
    	return release_date;
    }

    public void setRelease_date(java.sql.Date release_date) {
    	this.release_date = release_date;
    }

    public double getPrice() {
    	return price;
    }

    public void setPrice(double price) {
    	this.price = price;
    }

    public String getGenre() {
    	return genre;
    }

    public void setGenre(String genre) {
    	this.genre = genre;
    }

    public String getRating() {
    	return rating;
    }

    public void setRating(String rating) {
    	this.rating = rating;
    }
}