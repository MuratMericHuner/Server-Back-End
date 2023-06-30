package com.example.server.service;

import com.example.server.entities.Server;
import com.example.server.repo.ServerRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Optional;

import static com.example.server.entities.Status.SERVER_DOWN;
import static com.example.server.entities.Status.SERVER_UP;
import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j /*Log things out*/
public class ServerServiceImp implements ServerService{
    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new Server ", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        Server server = serverRepo.findByIpAddress(ipAddress);
        log.info("Pinging server :", server.getName());
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)? SERVER_UP : SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all the servers");
        /*method of() to return limited size*/
        return serverRepo.findAll(of(0,limit)).toList();
    }

    @Override
    public Server getServer(Long id) {
        return serverRepo.findById(id).orElseThrow(()->new IllegalStateException("There isn't such Server"));
    }

    @Override
    public Server updateServer(Server server) {
        log.info("Updating the Server ", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean deleteServer(Long id) {
        log.info("Deleting the Server ", id);
        Optional<Server> server = serverRepo.findById(id);
        if(!server.isPresent()) return false;
        serverRepo.deleteById(id);
        return true;
    }

}
