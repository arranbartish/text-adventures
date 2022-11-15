package au.bartish.game.ground.yard;

import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;

public class UnderTree extends MansionLocation {


  private String prefix = "";

  public UnderTree() {
    super("underTree");
  }

  public UnderTree(House house) {
    this();
    setHouse(house);
  }

  @Override
  public String getStory() {
    return getPrefix() + "The elf continues to mutter himself about a troll." +
      "\nHe shows an interest in you and waits for you to speak." +
       "The elf has in his hands \n" +
      ((this.isEmpty()) ? "nothing" : this.listItems()) +
       "\n\nWith the elf you an ask: " +
       "\n - to stop the conversation" +
       "\n - about the troll" +
       "\n - about the house" +
       "\n - why monsters are in the village";
  }

  @Override
  public String getQuestion() {
    return "What would you like to ask?";
  }

  @Override
  public Location doAction(String action) {
    if (action.equalsIgnoreCase("stop")
      || action.equalsIgnoreCase("leave")
      || action.equalsIgnoreCase("bye")
    ) {
      return getHouse().get("yard");
    } else if (action.equalsIgnoreCase("monster")
      || action.equalsIgnoreCase("monsters")
        ||action.equalsIgnoreCase("village")) {
      villageStory();
    }
    return this;
  }

  private String getPrefix() {
    String result = prefix;
    prefix = "";
    return result;
  }

  private void villageStory() {
    prefix = """
      On a beautiful da, the sorcerer who owns this mansion
      wonted to grow his power with an army of monstrous
      minions, but the spell went wrong and his monsters went
      wild and took the village and his life.
      
      """;
  }

  @Override
  public String getDisplayName() {
    return "underTree";
  }
}
