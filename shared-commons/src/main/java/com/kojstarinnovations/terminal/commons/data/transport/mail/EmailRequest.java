package com.kojstarinnovations.terminal.commons.data.transport.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private Map<String, Object> variables;
    private String template;
    private String mailTo;
    private String subject;
    private Map<String, Object> inlineResources;
    private Locale locale;
    private String sender;
}
