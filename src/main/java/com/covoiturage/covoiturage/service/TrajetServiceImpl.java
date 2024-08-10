package com.covoiturage.covoiturage.service;

import com.covoiturage.covoiturage.entity.Trajet;
import com.covoiturage.covoiturage.entity.User;
import com.covoiturage.covoiturage.repository.TrajetRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.util.List;

@Service
@Component("trajetService")
public class TrajetServiceImpl implements Services<Trajet> {
	private TrajetRepository trajetRepository;
	private ServiceRestController serviceRestController=new ServiceRestController();

	@Override
	public Trajet findById(int theId) {
		Trajet theTrajet = null;
		try {
			theTrajet = serviceRestController.findByIdTrajet(theId);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return theTrajet;
	}

	@Override
	public List<Trajet> findAll() {
		try {
			return serviceRestController.findAllTrajets();
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
	public void save(Trajet theTrajet) {
		try {
			serviceRestController.saveTrajet(theTrajet);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteById(int t) {
		// TODO Auto-generated method stub	
	}
}
