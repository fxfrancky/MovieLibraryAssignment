package com.assignment.movielibrary.service;

import java.util.List;

import com.assignment.movielibrary.model.Director;
import com.assignment.movielibrary.model.Movie;

public interface MovieLibraryService {
	
	/**
	 * Get All Movies
	 * @return
	 */
	public List<Movie> findAllMovies() throws Exception;
	
	/**
	 * Get a movie by title
	 * @param title
	 * @return
	 * @throws Exception 
	 */
	public Movie findMovieByTitle(String title) throws Exception;
	
	/**
	 * Add a movie
	 * @param movie
	 * @return
	 * @throws Exception 
	 */
	public Movie addMovie(Movie movie) throws Exception;
	
	/**
	 * Update a movie
	 * @param movie
	 * @return 
	 */
	public Movie updateMovie(Movie movie) throws Exception;
	
	/**
	 * Delete a movie
	 * @param title
	 */
	public void deleteMovie(String title) throws Exception;
	
	/**
	 * Get Every Movies for a director
	 * @param director
	 * @return
	 */
	public List<String> findAllMoviesTitleForADirector(String director) throws Exception;
	
	
	
}
