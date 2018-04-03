package au.bartish.game;

public interface Location extends ItemContainer{

    public String getStory();
    public String getQuestion();
    public Location doAction(String action, Backpack backpack);
    public String getDisplayName();
}
