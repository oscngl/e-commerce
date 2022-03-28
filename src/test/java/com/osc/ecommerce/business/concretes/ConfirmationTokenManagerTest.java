package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.dal.abstracts.ConfirmationTokenDao;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenManagerTest {

    private ConfirmationTokenManager confirmationTokenManager;

    @Mock
    private ConfirmationTokenDao confirmationTokenDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        confirmationTokenManager = new ConfirmationTokenManager(
                confirmationTokenDao
        );
    }

    @Test
    void canSave() {

        ConfirmationToken confirmationToken = new ConfirmationToken(
                new Admin()
        );

        confirmationTokenManager.save(confirmationToken);

        ArgumentCaptor<ConfirmationToken> confirmationTokenArgumentCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);
        verify(confirmationTokenDao).save(confirmationTokenArgumentCaptor.capture());
        ConfirmationToken capturedConfirmationToken = confirmationTokenArgumentCaptor.getValue();
        assertThat(capturedConfirmationToken).isEqualTo(confirmationToken);

    }

    @Test
    void canGetByToken() {

        String token = "token";
        ConfirmationToken confirmationToken = new ConfirmationToken(
                1,
                token,
                LocalDateTime.now(),
                null,
                LocalDateTime.now().plusMinutes(15),
                new Admin()
        );

        given(confirmationTokenDao.findByToken(token)).willReturn(confirmationToken);

        DataResult<ConfirmationToken> expected = confirmationTokenManager.getByToken(token);

        assertThat(expected.getData()).isEqualTo(confirmationToken);

    }

    @Test
    void canSetConfirmedAt() {

        String token = "token";

        given(confirmationTokenDao.findByToken(token)).willReturn(new ConfirmationToken());

        Result expected = confirmationTokenManager.setConfirmedAt(token);

        assertThat(expected.isSuccess()).isTrue();

    }

}