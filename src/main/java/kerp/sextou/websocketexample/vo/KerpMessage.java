package kerp.sextou.websocketexample.vo;

public class KerpMessage {
    private String text;
    private String sender;
    private String type;

    public KerpMessage() {}

    public KerpMessage(String text, String sender, String type) {
        this.text = text;
        this.sender = sender;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
