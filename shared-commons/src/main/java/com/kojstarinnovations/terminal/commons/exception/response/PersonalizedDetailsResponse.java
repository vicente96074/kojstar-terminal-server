package com.kojstarinnovations.terminal.commons.exception.response;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalizedDetailsResponse {
    private String message;
    private long duration;
    private Status status;

    @Override
    public String toString() {
        //To JSON
        return "{\"message\":\"" + message + "\",\"duration\":" + duration + ",\"status\":\"" + status + "\"}";
    }
}
