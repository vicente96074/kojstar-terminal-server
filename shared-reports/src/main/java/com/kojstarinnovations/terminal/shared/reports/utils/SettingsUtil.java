package com.kojstarinnovations.terminal.shared.reports.utils;

import com.kojstarinnovations.terminal.commons.data.payload.commons.SettingsStructureResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * SettingsUtil - Utility class for settings, to check if there are any null values and to complete the settings with default values
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@RequiredArgsConstructor
public class SettingsUtil {

    /**
     * Method to check if there are any null values in the settings
     *
     * @param settingsStructureResponse the settings to be checked
     * @return true if there are any null values, false otherwise
     */
    public static boolean hasAnyNullValue(SettingsStructureResponse settingsStructureResponse) {
        Class<?> clazz = settingsStructureResponse.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(settingsStructureResponse) == null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Internal server error");
            }
        }

        return false;
    }

    /**
     * Method to complete the settings with default values
     *
     * @param settingsStructureResponse the settings to be completed
     */
    public static void completeSettingsWithDefaults(SettingsStructureResponse settingsStructureResponse) {

        if (settingsStructureResponse.getId() == null || settingsStructureResponse.getId().isEmpty()) {
            throw new RuntimeException("Internal server error");
        }

        if (settingsStructureResponse.getUserId() == null || settingsStructureResponse.getUserId().isEmpty()) {
            throw new RuntimeException("Internal server error");
        }

        if (settingsStructureResponse.isGhSpanShowable()) {
            settingsStructureResponse.setGhSpanShowable(true);
        }

        if (settingsStructureResponse.getLanguage() == null) {
            settingsStructureResponse.setLanguage(Locale.getDefault().getLanguage());
        }

        if (settingsStructureResponse.getTheme() == null) {
            settingsStructureResponse.setTheme("light-theme");
        }

        if (settingsStructureResponse.getUrlProfilePicture() == null) {
            settingsStructureResponse.setUrlProfilePicture("default-profile-picture.png");
        }

    }
}