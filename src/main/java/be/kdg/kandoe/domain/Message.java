package be.kdg.kandoe.domain;

import java.time.LocalDateTime;

public class Message {
    private long id;
    private String sender;
    private String content;
    private long session;
    private LocalDateTime dateTime;

    public Message() {
    }

    public Message(long id, String sender, String content, long session, LocalDateTime dateTime) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.session = session;
        this.dateTime = dateTime;
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
