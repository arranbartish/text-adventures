package au.bartish.game.utilities;

import au.bartish.game.Listable;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.chomp;

public class StringBuilderListBuilder implements ListBuilder {

    public String listItems(Collection<Listable> items){
        final StringBuilder stringBuffer = new StringBuilder("\n");
        if(items.isEmpty()) {
            return null;
        } else {
            items.forEach(
                    item -> stringBuffer
                            .append("- ")
                            .append(item.getDisplayName())
                            .append('\n'));
        }
        return chomp(stringBuffer.toString());
    }

}
