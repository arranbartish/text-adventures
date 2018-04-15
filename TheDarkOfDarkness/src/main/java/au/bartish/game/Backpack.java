package au.bartish.game;

import org.apache.commons.lang3.ArrayUtils;

public class Backpack extends Inventory {

    public String getDisplayName() {
        return "backpack";
    }

    @Override
    public String[] listInventoryCommands() {
        return ArrayUtils.add(super.listInventoryCommands(), "backpack");
    }
}
