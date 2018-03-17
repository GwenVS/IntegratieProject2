package be.kdg.kandoe.repository.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MESSAGE")
public class MessageJpa {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String content;

    /*@ManyToOne(cascade = CascadeType.ALL)
    private GameSession session;*/

    @Column(nullable = false)
    private long session;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    public MessageJpa(String sender, String content, long session, LocalDateTime dateTime) {
        this.sender = sender;
        this.content = content;
        this.session = session;
        this.dateTime = dateTime;
    }
    public MessageJpa() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSession() {
        return session;
    }

    public void setSession(long session) {
        this.session = session;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
