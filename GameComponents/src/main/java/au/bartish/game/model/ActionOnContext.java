package au.bartish.game.model;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ActionOnContext extends ConditionalAction {

  private ActionOnContext(BiFunction<GameContext, GameContext.ActionContextBuilder, GameContext> action,
                          Predicate<GameContext> condition) {
    super(condition, action);
  }

  public static ConditionalAction with(BiFunction<GameContext, GameContext.ActionContextBuilder, GameContext> action, Predicate<GameContext> condition) {
    return new ActionOnContext(action, condition);
  }
}
