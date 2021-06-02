package com.photon.ratings.controller;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photon.ratings.models.Ratings;
import com.photon.ratings.models.UserRatings;

@RestController
@RequestMapping("/ratings")
public class RatingsEndpoint {

	@GetMapping("{movieId}")
	public Ratings getRatings(@PathVariable("movieId") String movieId) {
		return new Ratings("01Avengers", "Avengers", "Foo", "9.8");
	}

	@GetMapping("userRatings/{userId}")
	public UserRatings getMovies(@PathVariable("userId") String userId) {
		UserRatings userRatings = new UserRatings();
		userRatings.setUserRatings(Arrays.asList(
				new Ratings("01Avenger", "Avengers", "foo", "8.9"),
				new Ratings("01CapAme", "Captain America", "foo", "8.8"),
				new Ratings("01IrMan", "Iron-Man", "foo", "8.7"
			)));
		return userRatings;
	}
}
