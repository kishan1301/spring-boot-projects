package com.photon.catalogue.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.photon.catalogue.models.Catalogue;
import com.photon.catalogue.models.Movie;
import com.photon.catalogue.models.UserRatings;

@RestController
@RequestMapping("/catalogue")
public class MovieCatalogueEndpoint {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder builder;

	@GetMapping(path = "{userId}")
	public List<Catalogue> getCatalogueByUser(@PathVariable(name = "userId") String userId) {

		/*
		 * List<Ratings> ratings = Arrays.asList(new Ratings("01Avenger", "Avengers",
		 * "foo", "8.9"), new Ratings("01CapAme", "Captain America", "foo", "8.8"), new
		 * Ratings("01IrMan", "Iron-Man", "foo", "8.7"));
		 */
		UserRatings ratings = restTemplate.getForObject("http://ratings-info-service/ratings/userRatings/" + userId,
				UserRatings.class);

		return ratings.getUserRatings().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://movie-info-service/movie/" + rating.getMovieId(),
					Movie.class);
//			Movie movie = builder.build().get().uri("http://localhost:8082/movie/" + rating.getMovieId()).retrieve()
//					.bodyToMono(Movie.class).block();

			return new Catalogue(movie.getMovieName(), movie.getDescription(), rating.getRatings());
		}).collect(Collectors.toList());

//		return Collections.singletonList(new Catalogue("Avengers", "Description", "9.8"));
	}
}
