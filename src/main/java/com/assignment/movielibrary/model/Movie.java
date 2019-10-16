package com.assignment.movielibrary.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Movie {

	@NotNull
	private String title;
	@NotNull
	private String director;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern="dd/MM/yyyy")
	@NotNull
	private Date releaseDate;
	@NotNull
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
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getType() {
		return type;
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
