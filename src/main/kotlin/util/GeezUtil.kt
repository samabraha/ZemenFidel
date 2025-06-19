package util

object GeezUtil {
    val primaryLetters = getAllFirstLetters()

    private fun getAllFirstLetters(): List<Char> {
        val startAt = 0x1200
        return buildList {
            repeat(43) { i ->
                add((startAt + i * 8).toChar())
            }
        }
    }

    fun areSiblings(first: Char, second: Char): Boolean {
        TODO("Check sibling characters.")
        return false
    }
}