package au.bartish.game;

import java.util.Collection;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.lowerCase;

public abstract class ItemFactory<ARTIFACT extends GameArtifact> {

    public Item getItem(ARTIFACT artifact) {
        return artifact.get();
    }

    public Item getItem(String name) {

        return getItem(name, () -> Item.create(name));
    }

    public Item getItem(String name, Supplier<Item> itemSupplier) {
        @SuppressWarnings("unchecked")
        Collection<ARTIFACT> collection = getDefaultArtifact()
                .find(lowerCase(name));

        if (collection.isEmpty()){
            return itemSupplier.get();
        } else if (collection.size() > 1) {
            throw new RuntimeException("Fonnd more that 1 item");
        }

        return collection.stream().findAny().map(ARTIFACT::get).orElseGet(itemSupplier);
    }

    abstract public ARTIFACT getDefaultArtifact();
}
