package au.bartish.game.model;

import au.bartish.game.Builder;

import java.util.Map;

public class Message {

  private String content;

  private String contentKey;

  private Map<String, Object> contentContext;

  private boolean isContent;


  public String getContent() {
    return content;
  }

  private void setContent(String content) {
    this.content = content;
  }

  public String getContentKey() {
    return contentKey;
  }

  private void setContentKey(String contentKey) {
    this.contentKey = contentKey;
  }

  public Map<String, Object> getContentContext() {
    return contentContext;
  }

  private void setContentContext(Map<String, Object> contentContext) {
    this.contentContext = contentContext;
  }

  public boolean isContent() {
    return isContent;
  }

  private void setContent(boolean content) {
    isContent = content;
  }


  public static MessageBuilder builder() {
    return new MessageBuilder();
  }

  public static class MessageBuilder implements Builder<Message> {

    private Message instance = new Message();

    public MessageBuilder withContent(String content) {
      instance.setContent(content);
      instance.setContent(true);
      return this;
    }

    public MessageBuilder withContentKey(String contentKey) {
      instance.setContentKey(contentKey);
      instance.setContent(false);
      return this;
    }

    public MessageBuilder withContentContext(Map<String, Object> contentContext) {
      instance.setContentContext(contentContext);
      instance.setContent(false);
      return this;
    }

    public MessageBuilder withContentKeyAndContext(String contentKey, Map<String, Object> contentContext) {
      return withContentKey(contentKey).withContentContext(contentContext);
    }


    @Override
    public Message build() {
      final Message result = instance;
      instance = null;
      return result;
    }
  }
}
