package com.voltx.evgenee.runner;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocketIOServerRunner implements CommandLineRunner {

    private final SocketIOServer server;
    private final com.voltx.evgenee.socket.SocketIOHandler handler;

    @Override
    public void run(String... args) throws Exception {
        server.addListeners(handler);
        server.start();
    }

    @PreDestroy
    public void stop() {
        server.stop();
    }
}
