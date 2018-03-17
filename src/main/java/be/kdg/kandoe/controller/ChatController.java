package be.kdg.kandoe.controller;

import be.kdg.kandoe.domain.Message;
import be.kdg.kandoe.dto.MessageDto;
import be.kdg.kandoe.dto.converter.DtoConverter;
import be.kdg.kandoe.service.declaration.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    private ChatService chatService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/send/message/{sessionId}")
    @SendTo("/chatroom/{sessionId}")
    public MessageDto chatMessage(@DestinationVariable long sessionId, MessageDto messageDto) throws Exception {
        if(messageDto.getContent().equals("")|| messageDto.getContent()==null) return null;
        messageDto.setDateTime(LocalDateTime.now());
        return saveMessageAsync(messageDto,sessionId).get();
    }

    @SubscribeMapping("/subscribe/{sessionId}/chat")
    public List<MessageDto> subscribeToSession(@DestinationVariable long sessionId) throws ExecutionException, InterruptedException {
        return subscribeAsync(sessionId).get();
    }

    @Async
    public Future<List<MessageDto>> subscribeAsync(long sessionId) {
        return CompletableFuture.completedFuture(chatService.getLastMessages(50,sessionId).parallelStream().map(m-> DtoConverter.toMessageDto(m)).collect(Collectors.toList()));
    }


    @Async
    public Future<MessageDto> saveMessageAsync(MessageDto messageDto,long sessionId){
        Message m  = DtoConverter.toMessage(messageDto);
        m.setSession(sessionId);
        return CompletableFuture.completedFuture(DtoConverter.toMessageDto(chatService.saveMessage(m)));
    }
}