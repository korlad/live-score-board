package com.assessment.scoreBoard;

import java.util.List;

public interface ScoreBoard {
    Match startMatch(String homeTeam, String awayTeam);
    void updateMatchScore(String homeTeam, String awayTeam, Integer homeScore, Integer awayScore);
    void finishMatch(String homeTeam, String awayTeam);
    List<Match> getMatchSummary();
}
