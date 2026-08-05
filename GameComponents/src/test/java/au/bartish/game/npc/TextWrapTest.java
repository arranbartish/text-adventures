package au.bartish.game.npc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextWrapTest {

    @Test
    public void wraps_long_text_across_multiple_lines() {
        String longText = "word ".repeat(40).trim();

        String wrapped = TextWrap.wrap(longText);

        for (String line : wrapped.split(System.lineSeparator())) {
            assertThat(line.length()).isLessThanOrEqualTo(80);
        }
        assertThat(wrapped).contains(System.lineSeparator());
    }

    @Test
    public void short_text_is_left_unchanged() {
        assertThat(TextWrap.wrap("hello there")).isEqualTo("hello there");
    }

    @Test
    public void null_and_empty_are_passed_through() {
        assertThat(TextWrap.wrap(null)).isNull();
        assertThat(TextWrap.wrap("")).isEqualTo("");
    }
}
