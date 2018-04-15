package au.bartish.game;

public interface GameArtifact<ARTIFACT> extends Searchable<ARTIFACT>, Resolveable {

    ARTIFACT getDefaultArtifact();
}
