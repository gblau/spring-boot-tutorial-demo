package com.gblau;

import com.gblau.handler.DefaultTextWebSocketHandler;
import com.gblau.interceptor.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 开启WebSocket配置后，所有实现{@link WebSocketConfigurer}的配置都会加入到一个configurers列表中统一维护，实现多配制共存
 * @see DelegatingWebSocketConfiguration
 * @see EnableWebSocket 使用Import标签将{@link DelegatingWebSocketConfiguration}引入配置。
 * @author gblau
 * @date 2017-05-20
 */
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private DefaultTextWebSocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //注册处理拦截器,拦截url为socketServer的请求
        registry.addHandler(socketHandler, "/socketServer").addInterceptors(new WebSocketInterceptor());

        //注册SockJs的处理拦截器,拦截url为/sockjs/socketServer的请求
        registry.addHandler(socketHandler, "/sockjs/socketServer").addInterceptors(new WebSocketInterceptor()).withSockJS();
    }
}
