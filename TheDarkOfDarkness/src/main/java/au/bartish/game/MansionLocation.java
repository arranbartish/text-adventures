package au.bartish.game;

public abstract class MansionLocation extends BaseItemContainer implements Location {

  private final String key;

  private House house;

  public MansionLocation(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  protected House getHouse() {
    return house;
  }

  public void setHouse(House house) {
    this.house = house;
  }
}
