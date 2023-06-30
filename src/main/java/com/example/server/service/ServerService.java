package com.example.server.service;

import com.example.server.entities.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server server);

    Server ping(String ipAddress) throws IOException;
    Collection<Server> list(int limit);
    Server getServer(Long id);
    Server updateServer(Server server);
    Boolean deleteServer(Long id);
}
