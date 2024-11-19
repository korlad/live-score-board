package com.assessment.scoreBoard;

import java.time.LocalDateTime;

public interface Match {
    Integer getTotalScore();

    // Updates the score of the match
    void updateScore(Integer homeScore, Integer awayScore);

    // Marks the match as finished
    void finish();

    // Checks if the match is finished
    boolean isFinished();

    // Returns the time the match was created
    LocalDateTime getLocalDateTime();

    // Returns the home team
    String getHomeTeam();

    // Returns the away team
    String getAwayTeam();

    long getSequence();
}
