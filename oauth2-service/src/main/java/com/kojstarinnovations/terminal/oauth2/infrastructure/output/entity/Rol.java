package com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity;

import com.kojstarinnovations.terminal.shared.entity.RolEnt;
import jakarta.persistence.*;
import lombok.*;

import static com.kojstarinnovations.terminal.commons.data.constants.PersistenceConstants.AUTH_ROLES_TABLE_NAME;

/**
 * Rol Entity Class Definition - JPA to represent the rol table in the database
 * The id is generated pre-persist and the rolName is an enum.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = AUTH_ROLES_TABLE_NAME)
public class Rol extends RolEnt {

    @Id
    @Column(name = "id", length = 10)
    private String id;
}