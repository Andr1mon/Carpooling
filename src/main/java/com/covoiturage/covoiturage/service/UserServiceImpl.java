package com.covoiturage.covoiturage.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.covoiturage.covoiturage.repository.UserRepositoryImpl;
import com.covoiturage.covoiturage.web_service.API_EXT_INE;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.covoiturage.covoiturage.entity.User;
import com.covoiturage.covoiturage.entity.Role;

@Service
@Component("userService")
public class UserServiceImpl implements Services<User> {
	API_EXT_INE api_exterieure=new API_EXT_INE();
	private UserRepositoryImpl userRepositoryImpl;
private ServiceRestController serviceRestController=new ServiceRestController();
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findById(int theId) {
		User theUser;
		try {
			theUser = serviceRestController.findByIdUser(theId);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return theUser;
	}

	public  String encodeMDP(String mdp) {
		String newMDP=passwordEncoder.encode(mdp);
		return newMDP;
	}

	@Override
	public List<User> findAll() {
		try {
			return serviceRestController.findAllUsers();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findByUserName(String userName) {
		try {
			return serviceRestController.findByFirstNameUser(userName);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = null;
		try {
			user = serviceRestController.findByFirstNameUser(userName);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(),mapRolesToAuthorities(user.getRoles()));	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void save(User theUser) {
		theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
		try {
			if(api_exterieure.getListUsersAPI().contains(theUser.getIne())) {
				serviceRestController.saveUser(theUser);
			}
			else{

				serviceRestController.ErrorPage();
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteById(int t) {
		// TODO Auto-generated method stub
	}
}
