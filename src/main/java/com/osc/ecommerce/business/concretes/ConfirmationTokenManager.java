package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ConfirmationTokenService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.ConfirmationTokenDao;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenManager implements ConfirmationTokenService {

    private final ConfirmationTokenDao confirmationTokenDao;

    @Override
    public Result save(ConfirmationToken confirmationToken) {
        confirmationTokenDao.save(confirmationToken);
        return new SuccessResult("Confirmation token saved.");
    }

    @Override
    public DataResult<ConfirmationToken> getByToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenDao.findByToken(token);
        if(confirmationToken == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(confirmationToken);
        }
    }

    @Override
    public Result setConfirmedAt(String token) {
        ConfirmationToken confirmationToken = confirmationTokenDao.findByToken(token);
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenDao.save(confirmationToken);
        return new SuccessResult("Token confirmed.");
    }

}
