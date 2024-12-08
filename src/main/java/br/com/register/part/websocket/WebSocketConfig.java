package br.com.register.part.websocket;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Autowired
	private final WebsockerInterceptor websockerInterceptor;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// para onde o backend ira retornar as mensagens
		config.enableSimpleBroker("/topic");
		// onde o frontend ira enviar as mensagens
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// with sockjs
		registry.addEndpoint("/ws-message")
				.setAllowedOriginPatterns("*")
				.withSockJS();
		// without sockjs
		//registry.addEndpoint("/ws-message").setAllowedOriginPatterns("*");
	}


	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(websockerInterceptor);
	}
}