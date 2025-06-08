package model.deduction

import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals


class DeductionRoundTest {
    @Test
    fun `returns correct score for empty guess`() {
        val round = DeductionRound("abcde")

        assertThrows<IllegalArgumentException> {
            round.guess("")
        }
    }

    @Test
    fun `returns correct score for exact match`() {
        val round = DeductionRound("abcde")
        val score = round.guess("abcde")
        assertEquals(5, score.correct)
        assertEquals(0, score.misplaced)
        assertEquals(0, score.incorrect)
    }

    @Test
    fun `returns correct score for word with doubled letters`() {
        val round = DeductionRound("aabbcc")
        val score = round.guess("aabbcc")
        assertEquals(6, score.correct)
        assertEquals(0, score.misplaced)
        assertEquals(0, score.incorrect)
    }

    @Test
    fun `returns correct score for partial match with misplaced letters`() {
        val round = DeductionRound("abcde")
        val score = round.guess("abced")
        assertEquals(3, score.correct)
        assertEquals(2, score.misplaced)
        assertEquals(0, score.incorrect)
    }

    @Test
    fun `returns correct score for no matches`() {
        val round = DeductionRound("abcde")
        val score = round.guess("fghij")
        assertEquals(0, score.correct)
        assertEquals(0, score.misplaced)
        assertEquals(5, score.incorrect)
    }

    @Test
    fun `returns correct score for mixed matches`() {
        val round = DeductionRound("abcde")
        val score = round.guess("abfgh")
        assertEquals(2, score.correct)
        assertEquals(0, score.misplaced)
        assertEquals(3, score.incorrect)
    }

    @Test
    fun `returns correct score for repeated letters in guess`() {
        val round = DeductionRound("aabbcc")
        val score = round.guess("abcabc")
        assertEquals(2, score.correct)
        assertEquals(4, score.misplaced)
        assertEquals(0, score.incorrect)
    }

    @Test
    fun `returns correct score for wrong letters in guess`() {
        val round = DeductionRound("abcde")
        val score = round.guess("xyzab")
        assertEquals(0, score.correct)
        assertEquals(2, score.misplaced)
        assertEquals(3, score.incorrect)
    }

    @Test
    fun `returns correct score for all misplaced letters`() {
        val round = DeductionRound("abcde")
        val score = round.guess("ecdba")
        assertEquals(0, score.correct)
        assertEquals(5, score.misplaced)
        assertEquals(0, score.incorrect)
    }

}

