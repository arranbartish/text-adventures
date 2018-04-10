package au.bartish.game;

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Item implements Listable, Cloneable {

    private final String displayName;
    private final String name;

    private Item(String displayName, String name) {
        this.displayName = displayName;
        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Listable listable() {
        return this;
    }

    public static Item create(String name) {
        return new Item(name, lowerCase(name));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return create(displayName);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object that) {
        return reflectionEquals(this, that);
    }
}
