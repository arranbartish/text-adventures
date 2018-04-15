package au.bartish.game.basic;

import au.bartish.game.Inventory;

import static org.apache.commons.lang3.ArrayUtils.add;

public class Sack extends Inventory {

    @Override
    public String[] listInventoryCommands() {
        return add(super.listInventoryCommands(), "sack");
    }

    @Override
    public String getDisplayName() {
        return "sack";
    }
}
