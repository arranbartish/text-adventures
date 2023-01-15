package au.bartish.game.model;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public abstract class ConditionalAction {

  private final Predicate<GameContext> condition;
  private final BiFunction<GameContext, GameContext.ActionContextBuilder, GameContext> action;

  protected ConditionalAction(Predicate<GameContext> condition,
                              BiFunction<GameContext, GameContext.ActionContextBuilder, GameContext> action) {
    this.condition = condition;
    this.action = action;
  }


  public boolean isMet(final GameContext context) {
    return condition.test(context);
  }

  public GameContext apply(GameContext gameContext) {
    return Optional.of(gameContext)
      .filter(this::isMet)
      .map(context -> apply(context, GameContext.builderFromContext(context)))
      .orElse(gameContext);
  }

  public GameContext apply(GameContext gameContext, GameContext.ActionContextBuilder builder){
    return Optional.of(gameContext)
      .filter(this::isMet)
      .map(context -> action.apply(context, builder))
      .orElse(gameContext);
  }

}
