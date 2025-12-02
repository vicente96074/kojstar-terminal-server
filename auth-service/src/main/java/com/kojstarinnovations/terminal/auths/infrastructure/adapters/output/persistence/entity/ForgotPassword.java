package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.auths.vault.EncryptionConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ForgotPassword - This entity represents the persistence of the ForgotPassword entity in the database,
 * the id is generated pre-persist and the token is unique.
 * The user is a many-to-one relationship with the User entity.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "forgot_password")
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "code")
    @Convert(converter = EncryptionConverter.class)
    private String code;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "used")
    private Boolean used;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}