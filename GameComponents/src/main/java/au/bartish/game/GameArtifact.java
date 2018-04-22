package au.bartish.game;

public interface GameArtifact<ARTIFACT> extends Searchable<ARTIFACT>, Resolveable, Listable {

    ARTIFACT getDefaultArtifact();

}
