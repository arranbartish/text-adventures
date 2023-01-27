package au.bartish.game.model;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ActionStarts extends ConditionalAction {

  private ActionStarts(BiFunction<GameContext, GameContext.ActionContextBuilder, GameContext> action,
                       Predicate<GameContext> condition) {
    super(condition, action);
  }

  public static ConditionalAction with(BiFunction<GameContext, GameContext.ActionContextBuilder, GameContext> action, String prefix) {
    return new ActionStarts(action, gameContext -> gameContext.actionStartsWith(prefix));
  }
}
