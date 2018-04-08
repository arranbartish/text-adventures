package au.bartish.game;

public interface Location extends ItemContainer{

    String getStory();
    String getQuestion();
    Location doAction(String action);
    String getDisplayName();
}
