package au.bartish.game.pond;

import au.bartish.game.*;
import au.bartish.game.utilities.ListBuilder;
import au.bartish.game.utilities.StringBuilderListBuilder;

import java.util.Collection;

import static au.bartish.game.Action.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.collections.CollectionUtils.get;

public class ForbiddenPond extends BaseItemContainer implements Location {

    private final World world;
    private boolean isFrantic = true;
    private ActionResolver actionResolver = new ActionResolver();
    private Collection<Listable> availableActions = newArrayList(FLY_AWAY, ENTER_THE_POND, ASK_WHAT_IS_WRONG);
    private ListBuilder listBuilder = new StringBuilderListBuilder();

    public ForbiddenPond(World world) {
        this.world = world;
    }

    public String getStory() {
        return ((isFrantic) ? "You see the mermaid is distressed." : "The mermaid has told you that Rey missing and needs your help.") +
                "\n\nThe pond has blue water and purple and pink sparkles. " +
                "\nThis is the Forbidden pond ruled by Princess Rey of the Mermaids. You can:" +
                listBuilder.listItems(availableActions);
    }

    public String getQuestion() {
        return "What would you like to do?";
    }

    private Location doAction(Action action) {
        Location nextLocation = this;
        switch (action) {
            case FLY_AWAY:
                System.out.println("The mermaid is shocked to see you lift off the ground and fly away.");
                System.out.println("It's hunting season... too bad. The End.");
                nextLocation = world.get("exit");
                break;
        }
        return nextLocation;
    }

    public Location doAction(String action) {
        return doAction(actionResolver.resolve(action));
    }

    public String getDisplayName() {
        return "Forbidden Pond";
    }

    private class ActionResolver {

        Action resolve(String actionTerms) {
            Collection<Action> actions = DEFAULT.find(actionTerms);
            if(actions.size() == 1) {
                return (Action) get(actions,0);
            }
            return DEFAULT;
        }
    }
}
