package com.springtraining.loginjwt.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    private String clientSecret;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope")
    private Set<String> scopes;

    @Column(name = "redirect_uri")
    private String redirectUri;

    @Column(name = "postlogout_redirect_uri")
    private String postlogoutRedirectUri;

    @Column(nullable = false)
    private boolean active;
}
