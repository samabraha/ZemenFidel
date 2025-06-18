package util

import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger


object Log {
    val logger: Logger = Logger.getLogger("com.develogica.zemen_fidel")
    val enabled: Boolean = true
    val minLevel = Level.FINE

    val ignorePrefixes = listOf(
        Log::class.java.name,
        "java.lang.Thread",
        "java.util.logging.Logger"
    )

    inline fun info(tag: String = "", msg: () -> String) = log(Level.INFO, tag, msg)
    inline fun warn(tag: String = "", msg: () -> String) = log(Level.WARNING, tag, msg)
    inline fun debug(tag: String = "", msg: () -> String) = log(Level.FINE, tag, msg)
    inline fun error(tag: String = "", msg: () -> String) = log(Level.SEVERE, tag, msg)


    inline fun log(level: Level, tag: String, msg: () -> String) {
        if (!enabled || level.intValue() < minLevel.intValue()) return

        val record = LogRecord(level, "[$tag] ${msg()}")

        val stack = Thread.currentThread().stackTrace
        val caller: StackTraceElement = stack.firstOrNull { s ->
            ignorePrefixes.none { prefix -> s.className.startsWith(prefix) }
        } ?: stack[0]
//
        record.sourceClassName = caller.className
        record.sourceMethodName = caller.methodName

        logger.log(record)
    }
}
