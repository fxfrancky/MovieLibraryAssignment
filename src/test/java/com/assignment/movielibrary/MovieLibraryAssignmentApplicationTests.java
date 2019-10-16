package com.assignment.movielibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.assignment.movielibrary.model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieLibraryAssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieLibraryAssignmentApplicationTests {
	
	File moviesFile;

	@Before
	public void setUp() {
		moviesFile = new File("src/test/resources/moviestest.json");
	}
	
	/**
	 * initialize the movie file
	 * @throws Exception
	 */
	@Test
	public void initMoviesFile () throws Exception {
	    assert(moviesFile.exists());
		List<Movie> movies=new ArrayList<Movie>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		TypeReference<List<Movie>> typeReference = new TypeReference<List<Movie>>() {};
		movies =mapper.readValue(moviesFile, typeReference);
		mapper.writeValue(new File("src/main/resources/data/movies.json"), movies);
	}


	@Test
	public void contextLoads() {
	}
	
	@Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

   
    @Test
    public void testGetAllMovies() {
    HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);
       ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/movies",
       HttpMethod.GET, entity, String.class);  
       assertNotNull(response.getBody());
   }

   @Test
   public void testGetMovieByTitle() {
       Movie movie = restTemplate.getForObject(getRootUrl() + "/movies/Spiderman", Movie.class);
       System.out.println(movie.getTitle());
       assertNotNull(movie);
   }

   @Test
   public void testCreateMovie() {
	   Movie movie = new Movie();
	   movie.setTitle("The revenge");
	   movie.setDirector("Peter Schuman");
	   movie.setReleaseDate(new Date());
	   movie.setType("action");
      
       ResponseEntity<Movie> postResponse = restTemplate.postForEntity(getRootUrl() + "/movies", movie, Movie.class);
       assertNotNull(postResponse);
       assertNotNull(postResponse.getBody());
   }

   @Test
   public void testUpdateMovie() {
       String title = "The revenge";
       Movie movie = restTemplate.getForObject(getRootUrl() + "/movies/" + title, Movie.class);
       movie.setDirector("Peter Clinton");
	   movie.setReleaseDate(new Date());
	   movie.setType("action");
       restTemplate.put(getRootUrl() + "/movies/" + title, movie);
       Movie updatedMovie = restTemplate.getForObject(getRootUrl() + "/movies/" + title, Movie.class);
       assertNotNull(updatedMovie);
   }

   @Test
   public void testDeleteMovie() {
	    String title = "Spiderman";
	    Movie movie = restTemplate.getForObject(getRootUrl() + "/movies/" + title, Movie.class);
        assertNotNull(movie);
        restTemplate.delete(getRootUrl() + "/movies/" + title);
        try {
             movie = restTemplate.getForObject(getRootUrl() + "/movies/" + title, Movie.class);
        } catch (final HttpClientErrorException e) {
             assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
   }
}