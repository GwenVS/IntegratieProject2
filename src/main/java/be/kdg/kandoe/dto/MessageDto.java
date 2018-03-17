package be.kdg.kandoe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class MessageDto {
    private long id;
    private String sender;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT")
    private LocalDateTime dateTime;

    public MessageDto() {
    }

    public MessageDto(long id, String sender, String content, LocalDateTime dateTime) {
        this.id = id;
        this.sender = sender;
        this.content = content;
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




public LocalDateTime getDateTime() {
        return dateTime;
        }

public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        }
        }

