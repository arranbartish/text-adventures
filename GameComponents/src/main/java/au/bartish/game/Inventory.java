package au.bartish.game;

public abstract class Inventory extends BaseItemContainer {

    public String[] listInventoryCommands() {
        return new String[] {"inventory"};
    }

    public abstract String getDisplayName();
}
