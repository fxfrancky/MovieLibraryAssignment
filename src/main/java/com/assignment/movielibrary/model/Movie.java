package com.assignment.movielibrary.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Movie {

	@NotNull(message = "This field cant be empty")
	private String title;
	@NotNull(message = "This field cant be empty")
	private String director;
	@NotNull(message = "This field cant be empty")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate releaseDate;
	@NotNull(message = "This field cant be empty")
	private String type;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}

	public String getType() {
		return type;
	}
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Movie [title=" + title + ", directors=" + director + ", releaseDate=" + releaseDate + ", type=" + type
				+ "]";
	}
	
	
	
	
}
