package au.bartish.game.model;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class OneOfStringAction extends ConditionalAction {

  private OneOfStringAction(BiFunction<GameContext, GameContext.ActionContextBuilder, GameContext> action,
                           Predicate<GameContext> condition) {
    super(condition, action);
  }

  public static ConditionalAction of(BiFunction<GameContext, GameContext.ActionContextBuilder, GameContext> action, String... oneOf) {
    return new OneOfStringAction(action, gameContext -> gameContext.actionIsOneOf(oneOf));
  }
}
