package com.kojstarinnovations.terminal.us.application.data.request;

import com.kojstarinnovations.terminal.commons.data.transport.commons.ContactRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContactUSRequest extends ContactRequest {
    private String userId;
}