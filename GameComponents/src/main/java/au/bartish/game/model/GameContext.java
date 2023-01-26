package au.bartish.game.model;

import au.bartish.game.Builder;
import au.bartish.game.ItemContainer;
import au.bartish.game.Location;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GameContext {

  private String action;

  private Location current;

  private Location nextLocation;

  private List<ItemContainer> containersAvailable;

  private List<Message> messages;

  private List<Runnable> methods;

  private boolean gameOver = false;

  public boolean actionIsOneOf(String... actions) {
    return Stream.of(actions).anyMatch(potentialAction -> potentialAction.equalsIgnoreCase(action));
  }

  public boolean actionStartsWith(String prefix) {
    return StringUtils.startsWith(action, prefix);
  }

  public static ActionContextBuilder builder() {
    return new ActionContextBuilder();
  }

  public static ActionContextBuilder builderFromContextWithoutMessages(GameContext context) {
    return builder()
      .withCurrentLocation(context.getCurrentLocation())
      .withNextLocation(context.getNextLocation())
      .withAction(context.getAction())
      .withContainer(context.getContainersAvailable().stream().findAny().orElse(null));

  }

  public static ActionContextBuilder builderFromContext(GameContext context) {
    return builderFromContextWithoutMessages(context)
      .withMessages(context.getMessages());

  }


  public String getAction() {
    return action;
  }

  private void setAction(String action) {
    this.action = action;
  }

  public Location getCurrentLocation() {
    return current;
  }

  private void setCurrent(Location current) {
    this.current = current;
  }

  public Location getNextLocation() {
    return nextLocation;
  }

  private void setNextLocation(Location nextLocation) {
    this.nextLocation = nextLocation;
  }

  public List<ItemContainer> getContainersAvailable() {
    return Optional.ofNullable(containersAvailable).map(List::copyOf).orElseGet(List::of);
  }

  private void setContainersAvailable(List<ItemContainer> containersAvailable) {
    this.containersAvailable = Optional.ofNullable(this.containersAvailable)
      .stream().peek(List::clear)
      .findAny()
      .orElseGet(ArrayList::new);
    this.containersAvailable.addAll(containersAvailable);
  }

  private void addAvailableContainer(ItemContainer container) {
    this.containersAvailable = Optional.ofNullable(this.containersAvailable)
      .orElseGet(ArrayList::new);
    containersAvailable.add(container);
  }

  public List<Message> getMessages() {
    return Optional.ofNullable(messages).map(List::copyOf).orElseGet(List::of);
  }

  private void setMessages(List<Message> messages) {
    this.messages = Optional.ofNullable(this.messages)
      .stream()
      .peek(List::clear)
      .findAny()
      .orElseGet(ArrayList::new);
    this.messages.addAll(messages);
  }

  private void addMessages(List<Message> messages) {
    this.messages = Optional.ofNullable(this.messages)
      .orElseGet(ArrayList::new);
    this.messages.addAll(messages);
  }

  private void addMessage(Message message) {
    addMessages(List.of(message));
  }


  public List<Runnable> getMethods() {
    return Optional.ofNullable(methods).map(List::copyOf).orElseGet(List::of);
  }

  private void setMethods(List<Runnable> methods) {
    this.methods = Optional.ofNullable(this.methods)
      .stream()
      .peek(List::clear)
      .findAny()
      .orElseGet(ArrayList::new);
    this.methods.addAll(methods);
  }

  private void addMethods(List<Runnable> methods) {
    this.methods = Optional.ofNullable(this.methods)
      .orElseGet(ArrayList::new);
    this.methods.addAll(methods);
  }

  private void addMethod(Runnable method) {
    addMethods(List.of(method));
  }

  private void setGameOver() {
    gameOver = true;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public boolean isNotGameOver() {
    return !isGameOver();
  }

  public static class ActionContextBuilder implements Builder<GameContext> {

    private GameContext instance = new GameContext();

    @Override
    public GameContext build() {
      final GameContext result = instance;
      instance = null;
      return result;
    }

    public ActionContextBuilder withAction(String action) {
      instance.setAction(action);
      return this;
    }

    public ActionContextBuilder withCurrentLocation(Location currentLocation) {
      instance.setCurrent(currentLocation);
      return this;
    }

    public ActionContextBuilder withNextLocation(Location nextLocation) {
      instance.setNextLocation(nextLocation);
      return this;
    }

    public ActionContextBuilder withContainer(ItemContainer container) {
      instance.addAvailableContainer(container);
      return this;
    }

    public ActionContextBuilder addMessage(Message message) {
      instance.addMessage(message);
      return this;
    }

    public ActionContextBuilder addMessage(String content) {
      return addMessage(Message.builder().withContent(content).build());
    }


    public ActionContextBuilder withMessages(List<Message> messages) {
      instance.setMessages(messages);
      return this;
    }

    public ActionContextBuilder withMethod(Runnable method) {
      instance.addMethod(method);
      return this;
    }

    public ActionContextBuilder gameOver() {
      instance.setGameOver();
      return this;
    }
  }




}
