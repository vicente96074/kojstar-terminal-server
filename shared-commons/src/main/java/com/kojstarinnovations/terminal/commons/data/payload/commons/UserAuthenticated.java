package com.kojstarinnovations.terminal.commons.data.payload.commons;

import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GithubUserResponse;
import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GoogleUserResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticated {

    /**
     * This method is used to build a UserAuthenticated object from a UserResponse object
     *
     * @param response The user
     * @return A UserAuthenticated object
     */
    public static UserAuthenticated of(UserResponse response) {
        String fullName = response.getFirstName() + (response.getMiddleName() != null ? " " + response.getMiddleName() : "")
                + (response.getFirstSurname() != null ? " " + response.getFirstSurname() : "")
                + (response.getSecondSurname() != null ? " " + response.getSecondSurname() : "")
                + (response.getMarriageSurname() != null ? " " + response.getMarriageSurname() : "");

        return UserAuthenticated.builder()
                .sub(response.getId())
                .userSettingId(response.getUserSettingId())
                .email(response.getEmail())
                .fullName(fullName)
                .storeBranchId(response.getStoreBranchId())
                .storeId(response.getStoreId())
                .build();
    }

    /**
     * This method is used to build a UserAuthenticated object from a GoogleUserResponse object
     *
     * @param response The user
     * @return A UserAuthenticated object
     */
    public static UserAuthenticated of(GoogleUserResponse response) {
        String fullName = response.getGivenName();

        return UserAuthenticated.builder()
                .sub(response.getId())
                .userSettingId(response.getUserSettingId())
                .email(response.getEmail())
                .fullName(fullName)
                .storeBranchId(response.getStoreBranchId())
                .storeId(response.getStoreId())
                .build();
    }

    /**
     * This method is used to build a UserAuthenticated object from a GithubUserResponse object
     *
     * @param response The user
     * @return A UserAuthenticated object
     */
    public static UserAuthenticated of(GithubUserResponse response) {
        String fullName = response.getName();

        return UserAuthenticated.builder()
                .sub(response.getId())
                .userSettingId(response.getUserSettingId())
                .email(response.getEmail())
                .fullName(fullName)
                .storeBranchId(response.getStoreBranchId())
                .storeId(response.getStoreId())
                .build();
    }

    private String sub;
    private String userSettingId;
    private String email;
    private String fullName;
    private String storeBranchId;
    private String storeId;
}