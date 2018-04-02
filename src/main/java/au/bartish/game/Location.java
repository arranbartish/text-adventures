package au.bartish.game;

public interface Location {

    public String getStory();
    public String getQuestion();
    public Location doAction(String action, Backpack backpack);
}
