
package com.photon.ipl.processor;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.photon.ipl.data.MatchInputData;
import com.photon.ipl.models.Match;

public class MatchProcesssor implements ItemProcessor<MatchInputData, Match> {

	private static final Logger log = LoggerFactory.getLogger(MatchProcesssor.class);

	@Override
	public Match process(final MatchInputData matchData) throws Exception {
		Match match = new Match();

		match.setId(Long.parseLong(matchData.getId()));
		match.setCity(matchData.getCity());
		match.setMatchDate(LocalDate.parse(matchData.getDate()));
		match.setPlayerOfMatch(matchData.getPlayerOfMatch());
		match.setVenue(matchData.getVenue());

		String firstTeam, secondTeam;
		if ("bat".equalsIgnoreCase(matchData.getTossDecision())) {
			firstTeam = matchData.getTossWinner();
			secondTeam = matchData.getTossWinner().equals(matchData.getTeam1()) ? matchData.getTeam2()
					: matchData.getTeam1();
		} else {
			secondTeam = matchData.getTossWinner();
			firstTeam = matchData.getTossWinner().equals(matchData.getTeam1()) ? matchData.getTeam2()
					: matchData.getTeam1();
		}
		match.setTeam1(firstTeam);
		match.setTeam2(secondTeam);
		match.setTossDecision(matchData.getTossDecision());
		match.setTossWinner(matchData.getTossWinner());
		match.setMatchWinner(matchData.getWinner());
		match.setResult(matchData.getResult());
		match.setResultMargin(matchData.getResultMargin());
		match.setUmpire1(matchData.getUmpire1());
		match.setUmpire2(matchData.getUmpire2());

		log.info("Logger info :: {}", matchData);
		return match;
	}
}