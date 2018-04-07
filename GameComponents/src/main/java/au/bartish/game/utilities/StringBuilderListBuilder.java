package au.bartish.game.utilities;

import java.util.Collection;

public class StringBuilderListBuilder implements ListBuilder {

    public String listItems(Collection<String> items){
        StringBuffer stringBuffer = new StringBuffer("");
        if(items.isEmpty()) {
            return null;
        } else {
            for (String item : items) {
                stringBuffer.append("\n- " +  item);
            }
        }
        return stringBuffer.toString();
    }

}
