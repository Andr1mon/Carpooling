package com.covoiturage.covoiturage.entity;


import javax.persistence.*;

@Entity
@Table(name = "trajet")
public class Trajet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "annonce_id")
	private int annonceId;

	@Column(name = "est_accepte")
	private int estAccepte;

	@Column(name = "conducteur_id")
	private int conducteurId;

	public int getEstAccepte() {
		return estAccepte;
	}

	public void setEstAccepte(int estAccepte) {
		this.estAccepte = estAccepte;
	}

	public int getConducteurId() {
		return conducteurId;
	}

	public void setConducteurId(int conducteurId) {
		this.conducteurId = conducteurId;
	}

	public Trajet(int userId, int annonceId , int conducteurId, int estAccepte) {
		this.userId = userId;
		this.annonceId = annonceId;
		this.estAccepte = estAccepte;
		this.conducteurId = conducteurId;
	}

	public Trajet() {

	}

	public Trajet(int userId, int annonceId) {
		this.userId = userId;
		this.annonceId = annonceId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAnnonceId() {
		return annonceId;
	}

	public void setAnnonceId(int annonceId) {
		this.annonceId = annonceId;
	}
}
