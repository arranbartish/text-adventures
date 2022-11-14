package au.bartish.game;

import au.bartish.game.utilities.ClassLoaderPath;
import au.bartish.game.utilities.TextProvider;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Map;

public class FreemarkerTextProvider implements TextProvider<String, Map<String, Object>> {

  private final Configuration configuration;

  public FreemarkerTextProvider() {
    this.configuration = configureFreemarker();
  }

  @Override
  public String resolveText(String textKey) {
    return resolveText(textKey, Map.of());
  }

  @Override
  public String resolveText(String textKey, Map<String, Object> model) {
    try(OutputStream stream = new ByteArrayOutputStream(); PrintStream printStream = new PrintStream(stream); Writer out = new OutputStreamWriter(printStream)) {
      Template template = configuration.getTemplate(textKey + ".ftl");
      template.process(model, out);
      return stream.toString();
    } catch (Exception exception) {
      throw new RuntimeException("failed to render " + textKey, exception);
    }
  }

  private Configuration configureFreemarker() {
    try {
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

      cfg.setDirectoryForTemplateLoading(new ClassLoaderPath("templates/.keep").getPath().getParent().toFile());

      cfg.setDefaultEncoding("UTF-8");
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      cfg.setLogTemplateExceptions(false);
      cfg.setWrapUncheckedExceptions(true);
      cfg.setFallbackOnNullLoopVariable(false);
      return cfg;
    } catch (Exception exception) {
      throw new RuntimeException("Failed to initialize freemarker", exception);
    }
  }

}
