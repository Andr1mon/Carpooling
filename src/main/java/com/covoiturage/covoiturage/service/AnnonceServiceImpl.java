package com.covoiturage.covoiturage.service;

import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.util.List;

@Service
@Component("annonceService")
public class AnnonceServiceImpl implements Services<Annonce> {

	@Autowired
	private ServiceRestController serviceRestController=new ServiceRestController();

	@Override
	public Annonce findById(int theId) {
		Annonce theAnnonce = null;
		try {
			theAnnonce = serviceRestController.findByIdAnnonce(theId);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return theAnnonce;
	}

	@Override
	public List<Annonce> findAll() {

		try {
			return serviceRestController.findAllAnnonces();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		return null;
	}

	@Override
	public User findByUserName(String userName) {
		return null;
	}

	@Override
	@Transactional
	public void save(Annonce theAnnonce) {

		try {
			serviceRestController.saveAnnonce(theAnnonce);
		} catch (JsonProcessingException | MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteById(int t) {
		// TODO Auto-generated method stub		
	}
}
