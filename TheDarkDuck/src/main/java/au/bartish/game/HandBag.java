package au.bartish.game;

import static org.apache.commons.lang3.ArrayUtils.add;

public class HandBag extends Inventory {

    @Override
    public String[] listInventoryCommands() {
        return add(super.listInventoryCommands(), "handbag");
    }

    @Override
    public String getDisplayName() {
        return "handbag";
    }
}
