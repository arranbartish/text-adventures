package au.bartish.game.utilities;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.chomp;

public class StringBuilderListBuilder implements ListBuilder {

    public String listItems(Collection<String> items){
        final StringBuffer stringBuffer = new StringBuffer("\n");
        if(items.isEmpty()) {
            return null;
        } else {
            for (String item : items) {
                stringBuffer
                        .append("- ")
                        .append(item)
                        .append('\n');
            }
        }
        return chomp(stringBuffer.toString());
    }

}
