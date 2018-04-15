package au.bartish.game;

import java.util.function.Supplier;

public interface GameWrapper {

    void execute();

    void setWillContinue(Supplier<Boolean> willContinue);
}
