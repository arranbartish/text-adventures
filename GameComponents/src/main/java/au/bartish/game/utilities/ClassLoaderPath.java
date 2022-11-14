package au.bartish.game.utilities;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClassLoaderPath {

  private final String path;

  public ClassLoaderPath(final String path) {
    this.path = path;
  }

  public Path getPath(){
    try{
      URI uri = ClassLoader.getSystemResource(path).toURI();
      return  Path.of(uri);
    } catch (Exception exception) {
      throw new RuntimeException("Failed to read " + path, exception);
    }
  }
}
