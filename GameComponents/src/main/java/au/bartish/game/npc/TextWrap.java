package au.bartish.game.npc;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Wraps NPC-authored text to a fixed console width. LLM replies (and NPC
 * farewell/unavailable messages) are free-form prose and can otherwise run
 * far past a typical terminal width on one line.
 */
final class TextWrap {

    private static final int DEFAULT_WIDTH = 80;

    private TextWrap() {
    }

    static String wrap(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return WordUtils.wrap(text, DEFAULT_WIDTH, System.lineSeparator(), true);
    }
}
