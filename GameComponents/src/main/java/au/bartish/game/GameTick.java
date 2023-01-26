package au.bartish.game;

import au.bartish.game.model.ConditionalAction;
import au.bartish.game.model.GameContext;
import au.bartish.game.model.GameContext.ActionContextBuilder;
import au.bartish.game.model.Message;
import au.bartish.game.model.OneOfStringAction;
import au.bartish.game.utilities.TextProvider;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
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
    public GameContext tick() {
      GameContext gameContext = GameContext.builder()
        .withCurrentLocation(getCurrentLocation())
        .withContainer(getInventory())
        .build();

      gameContext = gameContext.getCurrentLocation()
        .getStory(gameContext);

      gameContext = gameContext.getCurrentLocation()
        .getQuestion(gameContext);

      gameContext
        .getMessages()
        .forEach(this::print);
      final String response = lowerCase(scanner.nextLine());

      gameContext = GameContext.builderFromContextWithoutMessages(gameContext)
        .withAction(response).build();
      gameContext = gameContext.getCurrentLocation().handleAction(gameContext);
      gameContext = globalActionHandler(gameContext);

      updateLocation(Optional.ofNullable(gameContext.getNextLocation()).orElse(gameContext.getCurrentLocation()));

      gameContext
        .getMessages()
        .forEach(this::print);

      return gameContext;
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


  private final Collection<ConditionalAction> globalActions =
    List.of(
      OneOfStringAction.of(
        (context, builder) -> builder.addMessage(
            format("your in a %s and it %s", getCurrentLocation().getDisplayName(),
              ((getCurrentLocation().isEmpty())? "has nothing in it": "contains:"+getCurrentLocation().listItems())))
          .build(),
        "look around"
      ),
      OneOfStringAction.of(
        (context, builder) -> {
          context.getCurrentLocation().moveAllItemsTo(context.getContainersAvailable().stream().findAny().orElseThrow());
          return builder.build();
        },
        "take all"
      ),
      OneOfStringAction.of(
        (context, builder) -> {
          context.getContainersAvailable().stream().findAny().orElseThrow().moveAllItemsTo(context.getCurrentLocation());
          return builder.build();
        },
        "drop all"
      ),
      OneOfStringAction.of(
        (context, builder) -> {
          context.getContainersAvailable().stream().findAny().orElseThrow().moveAllItemsTo(context.getCurrentLocation());
          return builder.build();
        },
        "drop all"
      )
    );

    private GameContext globalActionHandler(GameContext context) {

      ActionContextBuilder contextBuilder = GameContext.builderFromContext(context);


      if (context.actionIsOneOf("look around")){
        contextBuilder.addMessage(Message.builder().withContent(
          format("your in a %s and it %s", getCurrentLocation().getDisplayName(),
            ((getCurrentLocation().isEmpty())? "has nothing in it": "contains:"+getCurrentLocation().listItems()))).build());
        } else if (context.actionIsOneOf("take all")) {
          context.getCurrentLocation().moveAllItemsTo(getInventory());
        } else if (context.actionIsOneOf("drop all")) {
          getInventory().moveAllItemsTo(context.getCurrentLocation());
        } else if (context.actionStartsWith("take")) {
            Item queryItem = queryItem("take", context.getAction());
            if(!context.getCurrentLocation().moveItemTo(queryItem, getInventory())){
              contextBuilder.addMessage(Message.builder()
                .withContent(format("%s is not in the %s", queryItem.getDisplayName(), context.getCurrentLocation().getDisplayName())).build());
            }
        } else if (context.actionStartsWith("drop")) {
            Item queryItem = queryItem("drop", context.getAction());
            if(!getInventory().moveItemTo(queryItem, context.getCurrentLocation())){
              contextBuilder.addMessage(Message.builder()
              .withContent(format("%s is not in your %s", queryItem.getDisplayName(), getInventory().getDisplayName())).build());
            }
        } else if (context.actionIsOneOf(getInventory().listInventoryCommands())) {
        contextBuilder.addMessage(Message.builder()
            .withContent(
              format("your %s %s", context.getContainersAvailable().stream().findAny().orElseThrow().getDisplayName(), (context.getContainersAvailable().stream().findAny().orElseThrow().isEmpty())? "has nothing in it": "contains:"+ context.getContainersAvailable().stream().findAny().orElseThrow().listItems())
            )
          .build());
        }

      return contextBuilder.build();
    }

  @SuppressWarnings("unchecked")
  private Item queryItem(final String prefix, final String action) {
    final String queryItem = trim(action.replaceAll(prefix, ""));
    Collection<ARTIFACT> items = defaultArtifact.find(queryItem);
    return (isEmpty(items)) ? create(queryItem) : ((ARTIFACT) get(items, 0)).get();
  }

}
