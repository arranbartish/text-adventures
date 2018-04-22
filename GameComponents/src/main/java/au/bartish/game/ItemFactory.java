package au.bartish.game;

public class ItemFactory<ARTIFACT extends GameArtifact> {

    public Item getItem(ARTIFACT artifact) {
        return Item.create(artifact.getDisplayName());
    }


    public Item getItem(String name) {
        return Item.create(name);
    }
}
