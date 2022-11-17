package au.bartish.game;

import au.bartish.game.model.ActionContext;
import au.bartish.game.model.ActionContext.ActionContextBuilder;
import au.bartish.game.model.Message;
import au.bartish.game.utilities.TextProvider;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;

import static au.bartish.game.Item.create;
import static java.lang.String.format;
import static org.apache.commons.collections.CollectionUtils.get;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.trim;

public abstract class GameTick<ARTIFACT extends GameArtifact<ARTIFACT>> implements Game {

    private final GameArtifact<ARTIFACT> defaultArtifact;
    private final Scanner scanner;
    private final PrintStream out;
    private final ItemMover itemMover = new ItemMover();


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

      ActionContext actionContext = globalActionHandler(response, getCurrentLocation(), getInventory());

      actionContext = getCurrentLocation().handleAction(actionContext);

      updateLocation(actionContext.getNextLocation());

      actionContext
        .getMessages()
        .forEach(this::print);
    }

  private void print(Message message) {
    //noinspection unchecked
    String messageString = Optional.of(message)
      .filter(Message::isContent)
      .map(Message::getContent)
      .orElseGet(() -> getTextProvider().resolveText(message.getContentKey(), message.getContentContext()));
    out.println(messageString);
  }

  public abstract TextProvider getTextProvider();


    private ActionContext globalActionHandler(String action, Location location, ItemContainer container) {
      ActionContext context = ActionContext.builder()
        .withAction(action)
        .withCurrentLocation(location)
        .withContainer(container)
        .build();
      ActionContextBuilder contextBuilder = ActionContext.builderFromContext(context);


      if (context.actionIsOneOf("look around")){
        contextBuilder.addMessage(Message.builder().withContent(
          format("your in a %s and it %s", getCurrentLocation().getDisplayName(),
            ((getCurrentLocation().isEmpty())? "has nothing in it": "contains:"+getCurrentLocation().listItems()))).build());

        } else if (context.actionIsOneOf("take all")) {
          moveAllItems(location, getInventory());
        } else if (context.actionIsOneOf("drop all")) {
          moveAllItems(getInventory(), location);
        } else if (context.actionStartsWith("take")) {
            String queryItem = trim(context.getAction().replaceAll("take", ""));
            moveItemFrom(location, getInventory(), queryItem, "%s is not in the %s");
        } else if (context.actionStartsWith("drop")) {
            String queryItem = trim(context.getAction().replaceAll("drop ", ""));
            moveItemFrom(getInventory(), location, queryItem, "%s is not in your %s");
        } else if (context.actionIsOneOf(getInventory().listInventoryCommands())) {
            out.println(format("your %s %s", getInventory().getDisplayName(),
                    ((container.isEmpty())? "has nothing in it": "contains:"+container.listItems())));
        }

      return contextBuilder.build();
    }


  private void moveAllItems(ItemContainer from, ItemContainer to) {
    itemMover.moveAllItems(from, to);
  }

  private void moveItemFrom(ItemContainer from, ItemContainer to, String queryItem, String failurePattern) {
        Collection<ARTIFACT> items = defaultArtifact.find(queryItem);
        @SuppressWarnings("unchecked")
        Item item = (isEmpty(items)) ? create(queryItem) : ((ARTIFACT) get(items, 0)).get();
        if (!itemMover.moveItem(item, from, to)){
            out.println(format(failurePattern,
                    item.getDisplayName(),
                    from.getDisplayName()));
        }
    }
}
