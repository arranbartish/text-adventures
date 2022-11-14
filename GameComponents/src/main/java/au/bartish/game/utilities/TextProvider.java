package au.bartish.game.utilities;

public interface TextProvider<KeyT, ModelT> {

  String resolveText(KeyT textKey);

  String resolveText(KeyT textKey, ModelT model);

}
