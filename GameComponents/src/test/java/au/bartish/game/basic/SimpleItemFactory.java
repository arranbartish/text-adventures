package au.bartish.game.basic;

import au.bartish.game.ItemFactory;

import static au.bartish.game.basic.SimpleArtifact.DEFAULT;

public class SimpleItemFactory extends ItemFactory<SimpleArtifact> {

    @Override
    public SimpleArtifact getDefaultArtifact() {
        return DEFAULT;
    }
}
