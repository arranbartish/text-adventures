package au.bartish.game;

public interface Game {

    void welcome();

    void tick();

    Location getCurrentLocation();

    void updateLocation(Location newLocation);

    Inventory getInventory();
}
