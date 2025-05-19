package com._p1m.productivity_suite.security.filter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.security.service.normal.JwtService;
import com._p1m.productivity_suite.security.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthChannelInterceptor implements ChannelInterceptor {

	private final JwtService jwtService;
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (accessor == null)
			return message;
		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			String authHeader = accessor.getFirstNativeHeader("Authorization");

			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				throw new IllegalArgumentException("Missing or invalid Authorization header");
			}
			try {
				String token = authHeader.substring(7);
				UsernamePasswordAuthenticationToken userAuthToken = jwtService.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(userAuthToken);
				accessor.setUser(userAuthToken);
				accessor.getSessionAttributes().put("token", authHeader);
				log.info("WebSocket is connected by user: {}", userAuthToken.toString());
			} catch (UnauthorizedException ex) {
				throw new AccessDeniedException("JWT validation failed: " + ex.getMessage());
			}
		}

		return message;
	}
}
