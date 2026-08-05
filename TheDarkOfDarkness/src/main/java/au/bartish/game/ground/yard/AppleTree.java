package au.bartish.game.ground.yard;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.ItemContainer;
import au.bartish.game.Location;
import au.bartish.game.npc.DarkElf;
import au.bartish.game.npc.DarkOfDarknessQuestFlags;
import au.bartish.game.npc.GameStateSnapshot;
import au.bartish.game.npc.NpcRoster;

public class AppleTree extends BaseItemContainer implements Location {

    private final House house;
    private final NpcRoster roster = new NpcRoster(new DarkElf());

    public AppleTree(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You stand beneath the apple tree with the Dark Elf. " +
                "He mutters about a Troll on the first floor.\n\n" +
                "You can leave the apple tree, or talk to elf";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        ItemContainer inventory = house.getInventory();
        GameStateSnapshot state = new GameStateSnapshot(
                inventory.isEmpty() ? "nothing" : inventory.listItems(),
                DarkOfDarknessQuestFlags.describe(inventory));

        if (house.getConversationRunner().handle(action, roster, state)) {
            return this;
        }

        if (action.equalsIgnoreCase("leave") || action.equalsIgnoreCase("exit")) {
            return house.get("yard");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "apple tree";
    }
}
