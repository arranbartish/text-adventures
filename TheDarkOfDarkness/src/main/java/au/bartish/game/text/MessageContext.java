package au.bartish.game.text;

import au.bartish.game.Backpack;
import au.bartish.game.Builder;
import au.bartish.game.Inventory;
import au.bartish.game.Location;

public record MessageContext(Location location, Inventory inventory) {

  public static class MessageContextBuilder implements Builder<MessageContext> {

    private Location location;
    private Inventory inventory;

    public MessageContextBuilder setLocation(Location location) {
      this.location = location;
      return this;
    }

    public MessageContextBuilder setInventory(Inventory inventory) {
      this.inventory = inventory;
      return this;
    }

    @Override
    public MessageContext builder() {
      return new MessageContext(location, inventory);
    }
  }
}
