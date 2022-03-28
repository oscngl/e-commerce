package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.*;
import com.osc.ecommerce.core.adapters.abstracts.EmailSenderService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.ErrorDataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.core.utilities.results.SuccessDataResult;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import com.osc.ecommerce.entities.dtos.AdminDto;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import com.osc.ecommerce.entities.dtos.SupplierDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthManagerTest {

    private AuthManager authManager;

    @Mock
    private UserService userService;

    @Mock
    private AdminService adminService;

    @Mock
    private CustomerService customerService;

    @Mock
    private SupplierService supplierService;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authManager = new AuthManager(
                userService,
                adminService,
                customerService,
                supplierService,
                emailSenderService,
                confirmationTokenService
        );
    }

    @Test
    void canRegisterAdmin() {

        String token = "token";

        given(adminService.save(new AdminDto())).willReturn(new SuccessDataResult<>(token, null));

        DataResult<String> expected = authManager.registerAdmin(new AdminDto());

        assertThat(expected.getData()).isEqualTo(token);

    }

    @Test
    void canRegisterCustomer() {

        String token = "token";

        given(customerService.save(new CustomerDto())).willReturn(new SuccessDataResult<>(token, null));

        DataResult<String> expected = authManager.registerCustomer(new CustomerDto());

        assertThat(expected.getData()).isEqualTo(token);

    }

    @Test
    void canRegisterSupplier() {

        String token = "token";

        given(supplierService.save(new SupplierDto())).willReturn(new SuccessDataResult<>(token, null));

        DataResult<String> expected = authManager.registerSupplier(new SupplierDto());

        assertThat(expected.getData()).isEqualTo(token);

    }

    @Test
    void canConfirm() {

        String token = "token";

        String email = "email@gmail.com";
        Admin admin = new Admin();
        admin.setEmail(email);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                admin
        );

        given(confirmationTokenService.getByToken(token)).willReturn(new SuccessDataResult<>(confirmationToken, null));
        given(userService.getByConfirmedEmail(email)).willReturn(new ErrorDataResult<>(null, null));

        Result expected = authManager.confirm(token);

        assertThat(expected.isSuccess()).isTrue();

    }

}