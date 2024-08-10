package com.covoiturage.covoiturage.repository;

import com.covoiturage.covoiturage.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {

	}
