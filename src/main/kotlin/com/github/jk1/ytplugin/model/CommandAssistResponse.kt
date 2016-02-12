package com.github.jk1.ytplugin.model

import com.google.gson.JsonParser
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Main wrapper around youtrack command assist response response. It delegates further parsing
 * to CommandHighlightRange, CommandSuggestion and CommandPreview classes
 */
class CommandAssistResponse(stream: InputStream) {

    val highlightRanges: List<CommandHighlightRange>
    val suggestions: List<CommandSuggestion>
    val previews: List<CommandPreview>

    init {
        val root = JsonParser().parse(InputStreamReader(stream)).asJsonObject
        val ranges = root.getAsJsonObject("underline").getAsJsonArray("ranges")
        val suggests = root.getAsJsonObject("suggest").getAsJsonArray("items")
        val commands = root.getAsJsonObject("commands").getAsJsonArray("command")
        highlightRanges = ranges.map { CommandHighlightRange(it) }
        suggestions = suggests.map { CommandSuggestion(it) }
        previews = commands.map { CommandPreview(it) }
    }
}