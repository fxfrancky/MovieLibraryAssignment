package com.assignment.movielibrary.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.movielibrary.model.Movie;
import com.assignment.movielibrary.service.MovieLibraryService;

@Controller
@RequestMapping("/movies/")
public class MovieLibraryController {
	
	Logger logger = LoggerFactory.getLogger(MovieLibraryController.class);

	@Autowired
	private MovieLibraryService movieLibraryService;
	
	
	@GetMapping("signup")
    public String showSignUpForm(Movie movie) {
        return "add-movie";
    }
	
	@GetMapping("list")
    public String showUpdateForm(Model model) throws Exception {
        model.addAttribute("movies", movieLibraryService.findAllMovies());
        return "index";
    }

	 @PostMapping("/add")
	    public String addMovie(@Valid Movie movie, BindingResult result, Model model) throws Exception {
	        if (result.hasErrors()) {
	            return "add-movie";
	        }
	        movieLibraryService.addMovie(movie);
	        return "redirect:list";
	    }
    
    @GetMapping("edit/{title}")
    public String showUpdateForm(@PathVariable("title") String title, Model model) throws Exception {
    	Movie movie;
		try {
			movie = movieLibraryService.findMovieByTitle(title);
		} catch (Exception e) {
			throw new Exception("Invalid movie title:" + title);
		}
    	
        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @GetMapping("/delete/{title}")
    public String deleteMovie(@PathVariable("title") String title, Model model) throws Exception {
		movieLibraryService.deleteMovie(title);
        model.addAttribute("movies", movieLibraryService.findAllMovies());
        return "index";
    }
    
  
    @PostMapping("update/{title}")
    public String updateMovie(@PathVariable("title") String title, @Valid Movie movie, BindingResult result,
        Model model) throws Exception {
        if (result.hasErrors()) {
            movie.setTitle(title);
            return "update-movie";
        }

        movieLibraryService.addMovie(movie);
        model.addAttribute("movies", movieLibraryService.findAllMovies());
        return "index";
    }
    
	
}
