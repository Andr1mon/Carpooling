package com.covoiturage.covoiturage.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.covoiturage.covoiturage.entity.User;

@Component("Services")
public interface Services<T> extends UserDetailsService {
	public T findById(int theId);
	public List<T> findAll();
	public UserDetails loadUserByUsername(String userName);
	public User findByUserName(String userName);
	public void save(T t);
	public void deleteById(int t);
}
