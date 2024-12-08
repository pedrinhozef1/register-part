package br.com.register.part.websocket;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class WebSocketTextController {
	
	@Autowired
	SimpMessagingTemplate template;
	
	@PostMapping("/send")
	public ResponseEntity<Void> sendMessage(@RequestBody WebSocketDto textMessageDTO) {
		template.convertAndSend("/topic/message", textMessageDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@MessageMapping("/sendMessage")
	public void receiveMessage(@Payload WebSocketDto textMessageDTO) {
		// receive message from client
		log.info("/sendMessage -> {}", textMessageDTO.getMessage());
	}
	
	
	@SendTo("/topic/message")
	public WebSocketDto broadcastMessage(@Payload WebSocketDto textMessageDTO) {
		return textMessageDTO;
	}
}