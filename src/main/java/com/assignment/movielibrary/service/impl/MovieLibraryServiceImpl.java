package com.assignment.movielibrary.service.impl;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.assignment.movielibrary.model.Movie;
import com.assignment.movielibrary.service.MovieLibraryService;
import com.assignment.movielibrary.utils.LocalDateDeserializer;
import com.assignment.movielibrary.utils.LocalDateSerializer;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class MovieLibraryServiceImpl implements MovieLibraryService{
	
	Logger logger = LoggerFactory.getLogger(MovieLibraryServiceImpl.class);

	private final String jsonFileUrl= "/movies.json";
	Resource resourceJSon = new ClassPathResource("movies.json");
	

	@Override
	public List<Movie> findAllMovies() throws Exception {
		
		List<Movie> movies=new ArrayList<Movie>();
		try {
			ObjectMapper mapper = serializingObjectMapper();
			TypeReference<List<Movie>> typeReference = new TypeReference<List<Movie>>() {};
			InputStream inputStream = TypeReference.class.getResourceAsStream(jsonFileUrl);
			mapper.registerModule(new JavaTimeModule());
			movies = mapper.readValue(inputStream,typeReference); 
		} catch (Exception e) {
			throw new Exception("An error occured when retrieving AllMovies " +e.getMessage());
		}
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

		try {
			ObjectMapper objectMapper = serializingObjectMapper();
			List<Movie> movies = findAllMovies();
			movies.add(movie);
			// write customerObj object to movies.json file
			objectMapper.writeValue(resourceJSon.getFile(), movies);
			return movie;
		} catch (Exception e) {
			throw new Exception("An error occured when adding a movie addMovie " + e.getMessage());
		}
	}

	@Override
	public Movie updateMovie(Movie movie) throws Exception {
		
		Movie mv = findMovieByTitle(movie.getTitle());
		try {
			 ObjectMapper objectMapper = serializingObjectMapper();
			 mv.setReleaseDate(movie.getReleaseDate());
			 mv.setType(movie.getType());
		     mv.setDirector(movie.getDirector());
		     List<Movie> movies = findAllMovies();
		     List<Movie> cleanMovies = movies.stream().filter(p ->!p.getTitle().equals(mv.getTitle())).collect(Collectors.toList());
		     cleanMovies.add(mv);
			 objectMapper.writeValue(resourceJSon.getFile(), cleanMovies);
	 } catch (Exception e) {
		throw new Exception("An error occured when adding a movie addMovie " + e.getMessage());
	 }
		return mv;
		 
	}

	@Override
	public void deleteMovie(String title) throws Exception {

		try {
			List<Movie> movies = findAllMovies();
			ObjectMapper mapper = serializingObjectMapper();
			List<Movie> cleanMovies = movies.stream().filter(p ->!p.getTitle().equals(title)).collect(Collectors.toList());
			
			mapper.writeValue(resourceJSon.getFile(), cleanMovies);
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
	
	public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		// configure objectMapper for pretty input
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

}
