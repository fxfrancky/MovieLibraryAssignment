package com.assignment.movielibrary.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.movielibrary.model.Movie;
import com.assignment.movielibrary.service.MovieLibraryService;

@RestController
@RequestMapping("/api/v1")
public class MovieLibraryController {
	
	Logger logger = LoggerFactory.getLogger(MovieLibraryController.class);

	@Autowired
	private MovieLibraryService movieLibraryService;
	
	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> findAllMovies() throws Exception{
		return ResponseEntity.ok(movieLibraryService.getAllMovies());
	}
	
	@GetMapping("/movie/{title}")
	public ResponseEntity<Movie> findMovieByTitle(@PathVariable String title) throws Exception {
		Movie mv = movieLibraryService.getMovieByTitle(title);
		
		if (mv==null) {
        	logger.error("A movie with the tittle " + title + "  does not exist");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mv);
	}
	
	@PostMapping
	public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) throws Exception{
		  return ResponseEntity.ok(movieLibraryService.addMovie(movie));
	}
	
	@PutMapping("/{title}")
    public ResponseEntity<Movie> update(@PathVariable String title, @Valid @RequestBody Movie movie) throws Exception {
        if (movieLibraryService.getMovieByTitle(title)==null) {
        	logger.error("A movie with the tittle " + title + "  does not exist");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(movieLibraryService.updateMovie(movie));
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Movie> delete(@PathVariable String title) throws Exception {
        if (movieLibraryService.getMovieByTitle(title)==null) {
        	logger.error("A movie with the tittle " + title + "  does not exist");
            ResponseEntity.badRequest().build();
        }

        movieLibraryService.deleteMovie(title);

        return ResponseEntity.ok().build();
    }
	
	
}
