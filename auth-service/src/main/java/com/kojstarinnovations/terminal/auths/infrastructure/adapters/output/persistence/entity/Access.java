package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.shared.entity.AccessEnt;
import jakarta.persistence.*;
import lombok.*;

import static com.kojstarinnovations.terminal.commons.data.constants.PersistenceConstants.AUTH_ACCESSES_TABLE_NAME;

/**
 * Access - This entity represents the persistence of the Access entity in the database,
 * the id is generated pre-persist and the accessName is an enum.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = AUTH_ACCESSES_TABLE_NAME)
@Builder
public class Access extends AccessEnt {

    @Id
    private String id;
}