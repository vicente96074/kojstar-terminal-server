package com.kojstarinnovations.terminal.commons.data.payload.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StorageResponse - Payload for Storage
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageResponse {
    private String filename;
    private String fileDownloadUri;
    private String fileType;
    private Long size;
}
