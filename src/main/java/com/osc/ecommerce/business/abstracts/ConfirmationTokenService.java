package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;

public interface ConfirmationTokenService {

    Result save(ConfirmationToken confirmationToken);
    DataResult<ConfirmationToken> getByToken(String token);
    Result setConfirmedAt(String token);
}
