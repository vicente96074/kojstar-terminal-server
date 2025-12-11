package com.kojstarinnovations.terminal.us.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.UserDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * UserOP
 *
 * @author BalamKiche
 */
public interface UserOP extends OutputPort<UserDTO, UserResponse, String> {

    /**
     * existsByUsername
     *
     * @param username username
     * @return boolean
     */
    boolean existsByUsername(String username);

    /**
     * existsByEmail
     *
     * @param email email
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * getPageActiveByStoreId
     *
     * @param pageable pageable
     * @return Page<UserDTO>
     */
    Page<UserResponse> getPageUserActive(Pageable pageable);

    /**
     * updateSettingId
     *
     * @param id            the id
     * @param userSettingId the setting id
     */
    void updateUserSettingId(String id, String userSettingId);

    /**
     * Block user
     *
     * @param userId the user ID
     */
    void blockUser(String userId);

    /**
     * Get status by user id
     *
     * @param userId the user ID
     * @return Status the status
     */
    Status getStatusById(String userId);
}
