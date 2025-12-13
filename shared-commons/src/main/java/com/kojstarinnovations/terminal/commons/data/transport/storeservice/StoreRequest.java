package com.kojstarinnovations.terminal.commons.data.transport.storeservice;

import com.kojstarinnovations.terminal.commons.data.constants.I18nStoreConstants;
import com.kojstarinnovations.terminal.commons.data.enums.BusinessType;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequest {
    private Integer locationId;

    @DataRequired(message = I18nStoreConstants.EXCEPTION_STORE_DATA_REQUIRED_CEO)
    private String ceo;

    @DataRequired(message = I18nStoreConstants.EXCEPTION_STORE_DATA_REQUIRED_NIT)
    private String nit;

    @DataRequired(message = I18nStoreConstants.EXCEPTION_STORE_DATA_REQUIRED_NAME)
    private String name;

    @DataRequired(message = I18nStoreConstants.EXCEPTION_STORE_DATA_REQUIRED_DESCRIPTION)
    private String description;

    @DataRequired(message = I18nStoreConstants.EXCEPTION_STORE_DATA_REQUIRED_PHONE)
    private String phone;
    private String email;

    private BusinessType businessType;

    private String webSite;
}