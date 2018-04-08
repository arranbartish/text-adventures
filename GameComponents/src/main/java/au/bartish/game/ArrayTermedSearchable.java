package au.bartish.game;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;

public class ArrayTermedSearchable implements Searchable {

    private final String[] terms;
    private final Searchable searchableSource;

    public ArrayTermedSearchable(String[] terms, Searchable searchableSource) {
        this.terms = terms;
        this.searchableSource = searchableSource;
    }

    public boolean hasTerm(String term) {
        return contains(terms, term);
    }

    public <T extends Searchable> T[] all() {
        return searchableSource.all();
    }

    public <T extends Searchable> Collection<T> find(String term) {
        T[] entireSet = all();
        if (isEmpty(entireSet)) {
            return newArrayList();
        }

        try (Stream<T> set = stream(entireSet)){
             return set
                     .parallel()
                     .filter(item -> item.hasTerm(term))
                     .collect(toList());
        }
    }

}
