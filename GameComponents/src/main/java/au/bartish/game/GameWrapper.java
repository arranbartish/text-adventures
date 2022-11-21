package au.bartish.game;

import au.bartish.game.model.GameContext;

import java.util.function.Function;
import java.util.function.Supplier;

public interface GameWrapper {

    void execute();

    void setWillContinue(Function<GameContext, Boolean> willContinue);
}
