package com.assessment.scoreBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreBoardDefault implements ScoreBoard {
    private final Map<Match, Match> matches;
    private final MatchValidator matchValidator;

    public ScoreBoardDefault() {
        this.matches = new HashMap<>();
        this.matchValidator = new MatchValidatorImpl();
    }

    @Override
    public synchronized Match startMatch(String homeTeam, String awayTeam) {
        Match match = new GeneralMatch(homeTeam, awayTeam);

        if (matches.containsKey(match)) {
            throw new IllegalArgumentException("Match between " + homeTeam + " and " + awayTeam + " already exists.");
        }

        matches.put(match, match);
        return match;
    }

    @Override
    public void updateMatchScore(String homeTeam, String awayTeam, Integer homeScore, Integer awayScore) {
        // Create a Match object to identify the match by home and away teams
        Match matchKey = new GeneralMatch(homeTeam, awayTeam);
        Match match = matches.get(matchKey);

        matchValidator.validateMatch(match);

        // Update the score if the match is still active
        match.updateScore(homeScore, awayScore);
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        // Create a Match object to identify the match by home and away teams
        Match matchKey = new GeneralMatch(homeTeam, awayTeam);

        // Attempt to get the match from the map
        Match match = matches.get(matchKey);

        matchValidator.validateMatch(match);

        // Mark the match as finished
        match.finish();
    }

    @Override
    public List<Match> getMatchSummary() {
        return matches.values().stream()
                .sorted()
                .filter(match -> !match.isFinished())
                .collect(Collectors.toList());
    }
}
