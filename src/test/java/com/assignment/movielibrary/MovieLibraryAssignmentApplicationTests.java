package com.assignment.movielibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieLibraryAssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieLibraryAssignmentApplicationTests {
	
	Logger logger = LoggerFactory.getLogger(MovieLibraryAssignmentApplicationTests.class);
	
	File moviesFile;
	File moviesTestFile;
	Resource resource = new ClassPathResource("movies.json");
	Resource resourcetest = new ClassPathResource("moviestest.json");
	
	@Autowired
	private MovieLibraryService movieLibraryService;

	@Before
	public void setUp() {
		try {
			moviesTestFile = resourcetest.getFile();
			moviesFile = resource.getFile();
		} catch (IOException e) {
			logger.error("Fichier non trouvé : "+e.getMessage());
		}
	}
	
	/**
	 * initialize the movie file
	 * @throws Exception
	 */
	//@Test
	public void initMoviesFile () throws Exception {
	    assert(resource.exists());
		List<Movie> movies=new ArrayList<Movie>();
		movies =initTestMovies();
		ObjectMapper mapper = serializingObjectMapper();
		mapper.writeValue(moviesFile, movies);
	}
	
	public List<Movie> initTestMovies() throws Exception{
		assert(resourcetest.exists());
		List<Movie> movies=new ArrayList<Movie>();
		ObjectMapper mapper = serializingObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		TypeReference<List<Movie>> typeReference = new TypeReference<List<Movie>>() {};
		movies =mapper.readValue(moviesTestFile, typeReference);
		return movies;
	}

   
    @Test
    public void testGetAllMovies() throws Exception {
       initMoviesFile ();
       List<Movie> movies = movieLibraryService.findAllMovies();
       assertNotNull(movies);
       assertEquals(10, movies.size());
       for(Movie mv : movies) {
    	  System.out.print(" ***Movie in the list  ** : " +mv.getTitle());
       }
   }

   @Test
   public void testGetMovieByTitle() throws Exception {
	   initMoviesFile ();
       Movie mv = movieLibraryService.findMovieByTitle("Spiderman");
       assertNotNull(mv);
       assertEquals("Spiderman", mv.getTitle());
       assertEquals("Peter Clinton", mv.getDirector());
       assertEquals("action", mv.getType());
   }

   @Test
   public void testCreateMovie() throws Exception {
	   initMoviesFile ();
	   Movie movie = new Movie();
	   movie.setTitle("The revenge");
	   movie.setDirector("Peter Schuman");
	   movie.setReleaseDate(LocalDate.now());
	   movie.setType("action");
	   movieLibraryService.addMovie(movie);
       Movie mv = movieLibraryService.findMovieByTitle("The revenge");
       assertEquals("The revenge", mv.getTitle());
       assertEquals("Peter Schuman", mv.getDirector());
       assertEquals("action", mv.getType());
       assertEquals(LocalDate.now(), mv.getReleaseDate());
   }

   @Test
   public void testUpdateMovie() throws Exception {
	   initMoviesFile ();
       String title = "John Wick";
       Movie movie = movieLibraryService.findMovieByTitle(title);
       movie.setDirector("Peter Clinton");
	   movie.setReleaseDate(LocalDate.now());
	   movie.setType("Horror");
      
       Movie mv = movieLibraryService.updateMovie(movie);
       assertEquals("John Wick", mv.getTitle());
       assertEquals("Peter Clinton", mv.getDirector());
       assertEquals("Horror", mv.getType());
       assertEquals(LocalDate.now(), mv.getReleaseDate());
       
   }

   @Test
   public void testDeleteMovie() throws Exception {
	   initMoviesFile ();
	    String title = "Spiderman";
	    Movie movie = movieLibraryService.findMovieByTitle(title);
        assertNotNull(movie);
      
         movieLibraryService.deleteMovie(title);
         Movie mv = movieLibraryService.findMovieByTitle(title);
         assertNull(mv);
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
