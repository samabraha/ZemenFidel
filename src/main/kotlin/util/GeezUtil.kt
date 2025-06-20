package util

object GeezUtil {
    val primaryLetters = getAllRootLetters()
    val fidelGroupOverrides = mapOf(
        'ቈ' to 'ቀ',
        'ቘ' to 'ቐ',
        'ኰ' to 'ከ',
        'ዀ' to 'ኸ',
        'ጐ' to 'ገ',
    )

    private fun getAllRootLetters(): List<Char> {
        val startAt = 0x1200
        return buildList {
            repeat(43) { i ->
                add((startAt + i * 8).toChar())
            }
        }
    }

    fun Char.getRootLetter(): Char? {
        val code = this.code
        if (code < 0x122 || code > 0x1354) return null
        val base = code - ((code - 0x1200) % 8)
        var root = base.toChar()
        root = fidelGroupOverrides[root] ?: root
        return root.takeIf { root in primaryLetters }.also { println("$this $it") }
    }

    fun Char.isSiblingsWith(second: Char): Boolean {
        val root1 = this.getRootLetter()
        val root2 = second.getRootLetter()
        return root1 != null && root1 == root2
    }

    fun Char.getAllSiblings(): Set<Char> {
        val root = this.getRootLetter() ?: return emptySet()
        val rootCode = root.code

        return buildSet {
            for (i in rootCode..rootCode + 6) add(i.toChar())
        }.also { println(it) }
    }
}