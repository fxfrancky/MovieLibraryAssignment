package com.assignment.movielibrary.model;

import java.util.Set;

public class Movie {

	private String title;
	private Set<Director> directors;
	private String releaseDate;
	private String type;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Set<Director> getDirectors() {
		return directors;
	}
	public void setDirectors(Set<Director> directors) {
		this.directors = directors;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
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
		return "Movie [title=" + title + ", directors=" + directors + ", releaseDate=" + releaseDate + ", type=" + type
				+ "]";
	}
	
	
	
	
}
