package com.photon.movie.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photon.movie.models.Movie;

@RestController
@RequestMapping("/movie")
public class MovieEndpoint {

	@GetMapping(path = "{movieId}")
	public Movie getMovie(@PathVariable(name = "movieId") String movieId) {
		return new Movie(movieId, "Avengers", "MCU biggest movie");
	}
}
