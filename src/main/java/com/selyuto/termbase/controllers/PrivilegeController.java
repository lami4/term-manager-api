package com.selyuto.termbase.controllers;

import com.selyuto.termbase.models.Privilege;
import com.selyuto.termbase.services.PrivilegeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/privileges")
public class PrivilegeController {

    final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping("")
    public List<Privilege> getPrivileges() {
        return privilegeService.getPrivileges();
    }
}
