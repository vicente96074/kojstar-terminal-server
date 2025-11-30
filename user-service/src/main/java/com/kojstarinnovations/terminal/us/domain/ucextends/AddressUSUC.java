package com.kojstarinnovations.terminal.us.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.AddressUSResponse;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;
import com.kojstarinnovations.terminal.us.application.data.request.AddressUSRequest;

/**
 * AddressUSUC - Interface that handles Address use cases, including saving, deleting, retrieving, and updating Address.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface AddressUSUC extends UseCase<AddressUSRequest, AddressUSResponse, String> {
}