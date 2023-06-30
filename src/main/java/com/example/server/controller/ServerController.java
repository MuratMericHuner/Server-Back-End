package com.example.server.controller;

import com.example.server.entities.Response;
import com.example.server.entities.Server;
import com.example.server.service.ServerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

import static com.example.server.entities.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static java.util.Map.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serverService;
    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        Collection<Server> servers = serverService.list(30);
        if(servers.isEmpty()){
            return  ResponseEntity.ok(
                    Response
                            .builder()
                            .timestamps(now())
                            .message("There are no servers")
                            .status(NOT_FOUND)
                            .StatusCode(NOT_FOUND.value())
                            .build()
            );
        }
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timestamps(now())
                        .data(of("servers",serverService.list(30)))
                        .message("Servers retrieved")
                        .status(OK)
                        .StatusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timestamps(now())
                        .data(of("server",server))
                        .message(server.getStatus()== SERVER_UP? "Server Pinged": "Server is unavailable")
                        .status(OK)
                        .StatusCode(OK.value())
                        .build()
        );
    }
    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timestamps(now())
                        .data(of("servers",serverService.create(server)))
                        .message("Server created")
                        .status(CREATED)
                        .StatusCode(CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timestamps(now())
                        .data(of("deleted",serverService.deleteServer(id)))
                        .message("Server deleted")
                        .status(OK)
                        .StatusCode(OK.value())
                        .build()
        );
    }
}
