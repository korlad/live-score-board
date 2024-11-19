package com.assessment.scoreBoard;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public final class GeneralMatch implements Serializable, Comparable<Match>, Match {
    private static long counter = 0;
    private static final long serialVersionUID = 1;
    private final String homeTeam;
    private final String awayTeam;
    private final LocalDateTime localDateTime;
    private final long sequence;
    private Integer homeScore;
    private Integer awayScore;
    private boolean finished;

    public GeneralMatch(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.localDateTime = LocalDateTime.now();
        this.sequence = counter++;
        this.finished = false;
    }

    public void updateScore(Integer homeScore, Integer awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    @Override
    public Integer getTotalScore() {
        return homeScore + awayScore;
    }

    @Override
    public long getSequence() {
        return sequence;
    }

    public void finish() {
        this.finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    @Override
    public String getHomeTeam() {
        return this.homeTeam;
    }

    @Override
    public String getAwayTeam() {
        return this.awayTeam;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayScore + " " + awayTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneralMatch match)) return false;
        return Objects.equals(homeTeam, match.homeTeam) &&
                Objects.equals(awayTeam, match.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }

    @Override
    public int compareTo(Match other) {
        int scoreComparison = Integer.compare(other.getTotalScore(), this.getTotalScore());
        if (scoreComparison != 0) {
            return scoreComparison;
        }
        return Long.compare(other.getSequence(), this.getSequence());
    }
}
