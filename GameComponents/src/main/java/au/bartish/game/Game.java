package au.bartish.game;

import au.bartish.game.model.GameContext;

public interface Game {

    void welcome();

    GameContext tick();

    Location getCurrentLocation();

    void updateLocation(Location newLocation);

    Inventory getInventory();
}
