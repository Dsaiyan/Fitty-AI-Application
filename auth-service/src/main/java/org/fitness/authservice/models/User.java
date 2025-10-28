package org.fitness.authservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id ;
    @Column(unique = true)
    private String keycloakId;
    @Column(nullable = false, unique = true)
    private String email;
    private String firstName;
    private String lastName;
    @CreationTimestamp
    private LocalDateTime createdAt ;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
