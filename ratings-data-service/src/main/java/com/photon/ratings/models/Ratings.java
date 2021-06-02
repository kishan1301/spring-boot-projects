package com.photon.ratings.models;

public class Ratings {
	private String movieId;
	private String movieName;
	private String userId;
	private String ratings;

	public Ratings(String movieId, String movieName, String userId, String ratings) {
		this.movieId = movieId;
		this.movieName = movieName;
		this.userId = userId;
		this.ratings = ratings;
	}

	public Ratings() {

	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		return "Ratings [movieId=" + movieId + ", movieName=" + movieName + ", userId=" + userId + ", ratings="
				+ ratings + "]";
	}
}
