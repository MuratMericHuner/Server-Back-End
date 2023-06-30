package com.example.server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.AUTO;

@NoArgsConstructor
@Entity
@Data
@AllArgsConstructor
public class Server {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long serverId;
    @Column(unique = true)
    @NotEmpty(message = "IP cannot be empty or null")
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private Status status;

}
