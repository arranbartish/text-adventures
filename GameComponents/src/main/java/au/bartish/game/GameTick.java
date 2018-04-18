package au.bartish.game;

import org.apache.commons.collections.CollectionUtils;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

import static au.bartish.game.Item.create;
import static java.lang.String.format;
import static org.apache.commons.collections.CollectionUtils.get;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.StringUtils.lowerCase;

public abstract class GameTick<ARTIFACT extends GameArtifact> implements Game {

    private final GameArtifact<ARTIFACT> defaultArtifact;
    private final Scanner scanner;
    private final PrintStream out;

    public GameTick(GameArtifact<ARTIFACT> defaultArtifact, Scanner scanner, PrintStream out) {
        this.defaultArtifact = defaultArtifact;
        this.scanner = scanner;
        this.out = out;
    }

    @Override
    public void tick() {
        out.println(getCurrentLocation().getStory());
        out.println(getCurrentLocation().getQuestion());
        final String response = lowerCase(scanner.nextLine());
        globalActionHandler(response, getCurrentLocation(), getInventory());
        updateLocation(getCurrentLocation().doAction(response));
    }

    private void globalActionHandler(String action, Location location, ItemContainer backpack) {
        final ItemMover itemMover = new ItemMover();
        if (action.startsWith("take")) {
            String queryItem = action.replaceAll("take ", "");
            Collection<ARTIFACT> items = defaultArtifact.find(queryItem);
            @SuppressWarnings("unchecked")
            Item item = (isEmpty(items)) ? create(queryItem) : ((ARTIFACT) get(items, 0)).get();
            if (!itemMover.moveItem(item, location, getInventory())){
                out.println(format("%s is not in the %s",
                        item.getDisplayName(),
                        location.getDisplayName()));
            }
        } else if (contains(getInventory()
                .listInventoryCommands(), action)) {
            out.println(format("your %s %s", getInventory().getDisplayName(),
                    ((backpack.isEmpty())? "has nothing in it": "contains:"+backpack.listItems())));
        }
    }
}
