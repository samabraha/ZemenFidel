package model.deduction

import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals


class DeductionRoundTest {
    @Test
    fun `returns correct score for empty guess`() {
        val round = DeductionRound(word = "abcde")

        assertThrows<IllegalArgumentException> {
            round.guess("")
        }
    }

    @Test
    fun `returns correct score for exact match`() {
        val round = DeductionRound(word = "abcde")
        round.guess("abcde")
        val score = round.score
        assertEquals(5, score.correct)
        assertEquals(0, score.misplaced)
        assertEquals(0, score.incorrect)
    }

    @Test
    fun `returns correct score for word with doubled letters`() {
        val round = DeductionRound(word = "aabbcc")
        round.guess("aabbcc")
        val score = round.score
        assertEquals(6, score.correct)
        assertEquals(0, score.misplaced)
        assertEquals(0, score.incorrect)
    }

    @Test
    fun `returns correct score for partial match with misplaced letters`() {
        val round = DeductionRound(word = "abcde")
        round.guess("abced")
        val score = round.score
        assertEquals(3, score.correct)
        assertEquals(2, score.misplaced)
        assertEquals(0, score.incorrect)
    }

    @Test
    fun `returns correct score for no matches`() {
        val round = DeductionRound(word = "abcde")
        round.guess("fghij")
        val score = round.score
        assertEquals(0, score.correct)
        assertEquals(0, score.misplaced)
        assertEquals(5, score.incorrect)
    }

    @Test
    fun `returns correct score for mixed matches`() {
        val round = DeductionRound(word = "abcde")
        round.guess("abfgh")
        val score = round.score
        assertEquals(2, score.correct)
        assertEquals(0, score.misplaced)
        assertEquals(3, score.incorrect)
    }

    @Test
    fun `returns correct score for repeated letters in guess`() {
        val round = DeductionRound(word = "aabbcc")
        round.guess("abcabc")
        val score = round.score
        assertEquals(2, score.correct)
        assertEquals(4, score.misplaced)
        assertEquals(0, score.incorrect)
    }

    @Test
    fun `returns correct score for wrong letters in guess`() {
        val round = DeductionRound(word = "abcde")
        round.guess("xyzab")
        val score = round.score
        assertEquals(0, score.correct)
        assertEquals(2, score.misplaced)
        assertEquals(3, score.incorrect)
    }

    @Test
    fun `returns correct score for all misplaced letters`() {
        val round = DeductionRound(word = "abcde")
        round.guess("ecdba")
        val score = round.score
        assertEquals(0, score.correct)
        assertEquals(5, score.misplaced)
        assertEquals(0, score.incorrect)
    }

    @Test
    fun `returns correct score for all letters misplaced with duplicates`() {
        val round = DeductionRound(word = "SPEED")
        round.guess("ERASE")
        val guess = round.score
        assertEquals(0, guess.correct)
        assertEquals(3, guess.misplaced)
        assertEquals(2, guess.incorrect)
        assertEquals(
            guess.status,
            listOf(
                DeductionRound.MatchStatus.Misplaced,
                DeductionRound.MatchStatus.Incorrect,
                DeductionRound.MatchStatus.Incorrect,
                DeductionRound.MatchStatus.Misplaced,
                DeductionRound.MatchStatus.Misplaced
            )
        )
    }

    @Test
    fun `returns correct score for all letters misplaced with duplicates in guess`() {
        val round = DeductionRound(word = "ABCDEF")
        round.guess("ABCABC")
        val guess = round.score
        assertEquals(3, guess.correct)
        assertEquals(0, guess.misplaced)
        assertEquals(3, guess.incorrect)
        assertEquals(
            guess.status,
            listOf(
                DeductionRound.MatchStatus.Correct,
                DeductionRound.MatchStatus.Correct,
                DeductionRound.MatchStatus.Correct,
                DeductionRound.MatchStatus.Incorrect,
                DeductionRound.MatchStatus.Incorrect,
                DeductionRound.MatchStatus.Incorrect
            )
        )
    }
}

