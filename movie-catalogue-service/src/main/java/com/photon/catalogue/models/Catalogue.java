package com.photon.catalogue.models;

public class Catalogue {
	private String movieName;
	private String description;
	private String ratings;

	public Catalogue(String movieName, String description, String ratings) {
		this.movieName = movieName;
		this.description = description;
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		return "Catalogue [movieName=" + movieName + ", description=" + description + ", ratings=" + ratings + "]";
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}
}
