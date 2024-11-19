package com.assessment.scoreBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {
    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoardDefault();
    }

    @Test
    void testStartMatch() {
        Match match = scoreBoard.startMatch("Mexico", "Canada");
        assertNotNull(match);
        assertEquals("Mexico 0 - 0 Canada", match.toString());
    }

    @Test
    void testDuplicateMatchThrowsException() {
        scoreBoard.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> scoreBoard.startMatch("Mexico", "Canada"));
    }

    @Test
    void testUpdateMatchScore() {
        Match match = scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.updateMatchScore("Mexico", "Canada", 5, 3);
        assertEquals("Mexico 5 - 3 Canada", match.toString());
    }

    @Test
    void testUpdateMatchThatDoesNotExist() {
        // Attempt to update a match that hasn't been started yet
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateMatchScore("Mexico", "Canada", 1, 0);  // No such match exists
        });

        assertEquals("Match not found.", exception.getMessage());
    }

    @Test
    void testUpdateFinishedMatch() {
        // Start a match
        scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.updateMatchScore("Mexico", "Canada", 1, 0);  // Update the score

        // Finish the match
        scoreBoard.finishMatch("Mexico", "Canada");

        // Attempt to update the match after it has been finished
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            scoreBoard.updateMatchScore("Mexico", "Canada", 2, 1);  // Match has been finished, cannot update
        });

        assertEquals("Cannot process operation for a finished match.", exception.getMessage());
    }

    @Test
    void testFinishMatch() {
        Match match = scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.finishMatch("Mexico", "Canada");
        List<Match> summary = scoreBoard.getMatchSummary();
        assertFalse(summary.contains(match));
    }

    @Test
    void testFinishNonExistentMatchThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scoreBoard.finishMatch("Mexico", "Canada"));

    }

    @Test
    void testFinishAlreadyFinishedMatchThrowsException() {
        scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.finishMatch("Mexico", "Canada");

        assertThrows(IllegalStateException.class, () -> scoreBoard.finishMatch("Mexico", "Canada"));
    }

    @Test
    void testGetMatchSummary() {
        scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.updateMatchScore("Mexico", "Canada", 0, 5);

        scoreBoard.startMatch("Spain", "Brazil");
        scoreBoard.updateMatchScore("Spain", "Brazil", 10, 2);

        scoreBoard.startMatch("Germany", "France");
        scoreBoard.updateMatchScore("Germany", "France", 2, 2);

        scoreBoard.startMatch("Uruguay", "Italy");
        scoreBoard.updateMatchScore("Uruguay", "Italy", 6, 6);

        scoreBoard.startMatch("Argentina", "Australia");
        scoreBoard.updateMatchScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreBoard.getMatchSummary();

        assertEquals("Uruguay 6 - 6 Italy", summary.get(0).toString());
        assertEquals("Spain 10 - 2 Brazil", summary.get(1).toString());
        assertEquals("Mexico 0 - 5 Canada", summary.get(2).toString());
        assertEquals("Argentina 3 - 1 Australia", summary.get(3).toString());
        assertEquals("Germany 2 - 2 France", summary.get(4).toString());
    }

    @Test
    void testGetMatchSummaryEmpty() {
        List<Match> summary = scoreBoard.getMatchSummary();

        assertEquals(summary.size(), 0);
    }
}
