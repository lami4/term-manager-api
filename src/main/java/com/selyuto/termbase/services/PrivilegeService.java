package com.selyuto.termbase.services;

import com.selyuto.termbase.models.Privilege;
import com.selyuto.termbase.repositories.PrivilegeRepository;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public List<Privilege> getPrivileges() {
        return privilegeRepository.findAll();
    }

    public Collection<Privilege> getPrivilegesByUserId(Long userId) {
        return this.privilegeRepository.getPrivilegesByUserId(userId);
    }

}
