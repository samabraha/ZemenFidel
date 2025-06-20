package model.jumble

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class JumbleRoundTest {
    @Test
    fun `returns true for correct guess`() {
        val round = JumbleRound(word = "apple")
        assertTrue(round.guess("apple"))
    }

    @Test
    fun `returns false for incorrect guess`() {
        val round = JumbleRound(word = "apple")
        assertFalse(round.guess("banana"))
    }

    @Test
    fun `jumbled word is different from original word`() {
        val round = JumbleRound(word = "apple")
        assertNotEquals("apple", round.jumbledWord)
    }

    @Test
    fun `reset jumbled word`() {
        val round = JumbleRound("apple")
        val originalJumbled = round.jumbledWord
        round.reset()
        assertNotEquals(originalJumbled, round.jumbledWord)
    }


}