package au.bartish.game;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class ArrayTermedSearchableTest {

    private final SearchableSource source = new SearchableSource();
    private final String[] defaultTerms = new String[] {"one", "two"};
    private final ArrayTermedSearchable defaultSearchable = new ArrayTermedSearchable(defaultTerms, source);

    @Test
    public void all_returns_navigation_set_when_initialized_with_navigation() {
        assertThat(defaultSearchable.all(), is(arrayContaining(source)));
    }

    @Test
    public void hasTerm_will_return_true_when_term_exists() {
        assertThat(defaultSearchable.hasTerm("one"), is(true));
    }

    @Test
    public void hasTerm_will_return_false_when_term_does_not_exists() {
        assertThat(defaultSearchable.hasTerm("blah"), is(false));
    }

    @Test
    public void hasTerm_will_return_false_when_term_is_null() {
        assertThat(defaultSearchable.hasTerm(null), is(false));
    }


    @Test
    public void find_by_default_will_return_an_empty_collection() {
        Collection<Searchable> result = defaultSearchable.find("go");
        assertThat(result, is(empty()));
    }

    @Test
    public void find_by_default_will_return_a_null_collection() {
        Collection<Searchable> result = defaultSearchable.find("go");
        assertThat(result, is(notNullValue()));
    }

    private class SearchableSource implements Searchable {

        @Override
        public boolean hasTerm(String term) {
            return false;
        }

        @Override
        public <T extends Searchable> T[] all() {
            return (T[]) new SearchableSource[] {this};
        }

        @Override
        public <T extends Searchable> Collection<T> find(String term) {
            return null;
        }
    }

}