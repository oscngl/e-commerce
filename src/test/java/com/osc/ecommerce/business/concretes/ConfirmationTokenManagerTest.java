package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.ConfirmationTokenDao;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenManagerTest {

    private ConfirmationTokenManager confirmationTokenManager;

    @Mock
    private ConfirmationTokenDao confirmationTokenDao;

    @BeforeEach
    void setUp() {
        confirmationTokenManager = new ConfirmationTokenManager(confirmationTokenDao);
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

        int id = 1;
        String token = "token";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime confirmedAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(15);
        Admin user = new Admin();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                id,
                token,
                createdAt,
                confirmedAt,
                expiresAt,
                user
        );

        when(confirmationTokenDao.findByToken(token)).thenReturn(confirmationToken);

        DataResult<ConfirmationToken> expected = confirmationTokenManager.getByToken(token);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getToken()).isEqualTo(token);
        assertThat(expected.getData().getCreatedAt()).isEqualTo(createdAt);
        assertThat(expected.getData().getConfirmedAt()).isEqualTo(confirmedAt);
        assertThat(expected.getData().getExpiresAt()).isEqualTo(expiresAt);
        assertThat(expected.getData().getUser()).isEqualTo(user);

    }

}