package com.assignment.movielibrary.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.assignment.movielibrary.model.Director;
import com.assignment.movielibrary.model.Movie;
import com.assignment.movielibrary.service.MovieLibraryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class MovieLibraryServiceImpl implements MovieLibraryService{
	
	Logger logger = LoggerFactory.getLogger(MovieLibraryServiceImpl.class);


	@Override
	public List<Movie> findAllMovies() throws Exception {
		
		List<Movie> movies=new ArrayList<Movie>();
	//	try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Movie>> typeReference = new TypeReference<List<Movie>>() {};
			InputStream inputStream = TypeReference.class.getResourceAsStream("classpath:data/movies.json");
			movies = mapper.readValue(inputStream,typeReference);
		//} catch (Exception e) {
		//	throw new Exception("An error occured when retrieving AllMovies " +e.getMessage());
	//	}
		return movies;
	}

	@Override
	public Movie findMovieByTitle(String title) throws Exception {
		
		Movie movie = findAllMovies().stream().
				filter(p-> p.getTitle().equalsIgnoreCase(title)).
				findFirst().orElse(null);
		return movie;
	}

	@Override
	public Movie addMovie(Movie movie) throws Exception {

	//	try {
			ObjectMapper objectMapper = new ObjectMapper();
			// configure objectMapper for pretty input
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			// write customerObj object to movies.json file
			objectMapper.writeValue(new File("classpath:data/movies.json"), movie);

			return movie;
	//	} catch (Exception e) {
	//		throw new Exception("An error occured when adding a movie addMovie " + e.getMessage());
	//	}
	}

	@Override
	public Movie updateMovie(Movie movie) throws Exception {
		
		Movie mv = findMovieByTitle(movie.getTitle());
		//try {
			 ObjectMapper objectMapper = new ObjectMapper();
			 objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			 mv.setReleaseDate(movie.getReleaseDate());
			 mv.setType(movie.getType());
		     mv.setDirector(movie.getDirector());
			 objectMapper.writeValue(new File("classpath:data/movies.json"), movie);
	//} catch (Exception e) {
		//throw new Exception("An error occured when adding a movie addMovie " + e.getMessage());
	 //}
		return mv;
		 
	}

	@Override
	public void deleteMovie(String title) throws Exception {

		try {
			List<Movie> movies = findAllMovies();
			ObjectMapper mapper = new ObjectMapper();
			for (Movie mv : findAllMovies()) {
				if (mv.getTitle().equals(title)) {
					movies.remove(mv);
				}
			}
			mapper.writeValue(new File("classpath:data/movies.json"), movies);
		} catch (Exception e) {
			throw new Exception("An error occured when deleting a movie deleteMovie " + e.getMessage());
		}
	}

	@Override
	public List<String> findAllMoviesTitleForADirector(String director) throws Exception {
		List<Movie> movies=findAllMovies().stream().filter(p ->p.getDirector().equals(director)).collect(Collectors.toList());
		List<String> directorMovies = new ArrayList<String>();
		for(Movie mv : movies ) {
				if(director.equals(mv.getDirector())) {
					directorMovies.add(mv.getTitle());
				}
		}
		return directorMovies;
	}

}
