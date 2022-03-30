package com.osc.ecommerce.api.controllers;

import com.osc.ecommerce.business.abstracts.RoleService;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.dtos.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RoleService roleService;

    @PostMapping("/save")
    public Result save(@RequestBody @Valid RoleDto roleDto) {
        return roleService.save(roleDto);
    }

}
