package au.bartish.game.model;

import au.bartish.game.Builder;
import au.bartish.game.ItemContainer;
import au.bartish.game.Location;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ActionContext {

  private String action;

  private Location current;

  private Location nextLocation;

  private List<ItemContainer> containersAvailable;

  private List<Message> messages;

  public boolean actionIsOneOf(String... actions) {
    return Stream.of(actions).anyMatch(potentialAction -> potentialAction.equalsIgnoreCase(action));
  }

  public boolean actionStartsWith(String prefix) {
    return StringUtils.startsWith(action, prefix);
  }

  public static ActionContextBuilder builder() {
    return new ActionContextBuilder();
  }

  public static ActionContextBuilder builderFromContext(ActionContext context) {
    return builder()
      .withCurrentLocation(context.getCurrentLocation())
      .withNextLocation(context.getNextLocation())
      .withAction(context.getAction())
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
    this.messages = Optional.ofNullable(this.messages)
      .orElseGet(ArrayList::new);
    this.messages.add(message);
  }


  public static class ActionContextBuilder implements Builder<ActionContext> {

    private ActionContext instance = new ActionContext();

    @Override
    public ActionContext build() {
      final ActionContext result = instance;
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

    public ActionContextBuilder withMessages(List<Message> messages) {
      instance.setMessages(messages);
      return this;
    }
  }




}
