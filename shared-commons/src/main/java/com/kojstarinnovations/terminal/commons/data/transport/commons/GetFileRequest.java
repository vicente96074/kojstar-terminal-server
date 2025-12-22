package com.kojstarinnovations.terminal.commons.data.transport.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GetFileRequest - Transport object for Get File with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetFileRequest {
    private String filename;
    private String category;
}