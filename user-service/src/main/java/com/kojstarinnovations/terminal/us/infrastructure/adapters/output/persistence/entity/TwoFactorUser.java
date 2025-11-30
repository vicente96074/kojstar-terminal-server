package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.entity.TwoFactor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "two_factor")
@EqualsAndHashCode(callSuper = true)
@Table(name = "two_factor", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "two_factor_type"}))
public class TwoFactorUser extends TwoFactor {

    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "two_factor_secret")
    private String twoFactorSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUIDHelper.generateUUID("2FA", 15);
        }
    }
}