package com.demo.client.config.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@Slf4j
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        log.debug("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
            String[] splitPath = request.getURI().getPath().split("/");
            if (splitPath.length == 3) {
                String type = splitPath[2];
                attributes.put("type", type);
            } else {
                log.warn("link error {}", request.getURI().getPath());
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);

    }

}