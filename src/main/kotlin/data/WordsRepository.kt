package data

import kotlinx.serialization.json.Json
import java.nio.file.Files
import kotlin.io.path.Path

class WordsRepository {
    val words: List<String>

    init {
        val filePath = Path(path = "./word_list.json")
        val wordList = Json.decodeFromString<Map<String, Set<String>>>(Files.readString(filePath))
        words = wordList.flatMap { it.value }
            .filterNot { it.contains(' ') || it.length < 3 }
    }
}
