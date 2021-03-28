package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin loadUserByUsername(String username) throws UsernameNotFoundException {
		return adminRepository.findByUsername(username);
	}
}
