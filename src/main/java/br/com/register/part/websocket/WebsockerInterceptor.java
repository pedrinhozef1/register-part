package br.com.register.part.websocket;


import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class WebsockerInterceptor implements ChannelInterceptor {
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(message);
        if (headerAccessor.getSessionId() != null) {
            String sessionId = headerAccessor.getSessionId();
            String userName = (headerAccessor.getUser() != null) ? headerAccessor.getUser().getName() : "without_user";
            log.info("User -> {} :: SessionId -> {}", userName, sessionId);
        }
    }
}
