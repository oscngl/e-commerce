package com.osc.ecommerce.api.controllers;

import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.entities.abstracts.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/getByEmail")
    public DataResult<User> getByEmail(String email) {
        return userService.getByConfirmedEmail(email);
    }

}
