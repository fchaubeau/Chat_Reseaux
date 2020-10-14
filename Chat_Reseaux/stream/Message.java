package Chat_Reseaux.stream;

public class Message {
    private String content;
    private String sender;
    boolean connexion = false;

    public Message(String sender, String content) {
        this.content = content;
        this.sender = sender;
    }

    public Message (String sender, boolean connexion) {
        this.sender = sender;
        this.content = null;
        this.connexion = connexion;
    }
    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String toString() {
        if (content == null) {
            return sender + (connexion ? " a rejoint" : " a quitté") + " le chat.";
        }
        return sender + " : " + content;
    }
}
