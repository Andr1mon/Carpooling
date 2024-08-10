package com.covoiturage.covoiturage.entity;

import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "annonce")
public class Annonce  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "date")
	/*private Date date;*/
	//@DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime date;

	@Column(name = "depart")
	private String depart;

	@Column(name = "arrive")
	private String arrive;

	@Column(name = "latitude1")
	private double latitude1;

	@Column(name = "longitude1")
	private double longitude1;


	@Column(name = "latitude2")
	private double latitude2;

	@Column(name = "longitude2")
	private double longitude2;

	@Column(name = "commentaire")
	private String commentaire;

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public double getLatitude1() {
		return latitude1;
	}

	public void setLatitude1(double latitude1) {
		this.latitude1 = latitude1;
	}

	public double getLongitude1() {
		return longitude1;
	}

	public void setLongitude1(double longitude1) {
		this.longitude1 = longitude1;
	}

	public double getLatitude2() {
		return latitude2;
	}

	public void setLatitude2(double latitude2) {
		this.latitude2 = latitude2;
	}

	public double getLongitude2() {
		return longitude2;
	}

	public void setLongitude2(double longitude2) {
		this.longitude2 = longitude2;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	@Column(name = "duree")
	private String duree;

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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
}
