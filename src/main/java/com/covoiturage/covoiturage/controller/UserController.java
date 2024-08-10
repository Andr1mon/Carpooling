package com.covoiturage.covoiturage.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.covoiturage.covoiturage.entity.Annonce;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.covoiturage.covoiturage.entity.User;

@Controller
@Component("cont")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	PresentationRestController presentation= new PresentationRestController();
	
	@GetMapping("/")
	public String LoginPage() {
		return "login-page";
	}
		
	@GetMapping("/home")
	public String Homepage() {		
		return "home";
	}

	@GetMapping("/registration")
	public String UserRegistration(@ModelAttribute("user") User theUser) {
		return "saveUser";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User theUser,
						   @Nullable @RequestParam("newPassword") String value) throws MalformedURLException, ParseException, JsonProcessingException {

		presentation.saveUser(theUser, value);
		if (PresentationRestController.errorLoginv == 0) {
			return "redirect:/registration-success";
		}
		else{
			PresentationRestController.errorLoginv = 0;
			return "errorLogin";
		}
	}

	@GetMapping("/registration-success")
	public String UserRegistrationSuccess(@ModelAttribute("user") User theUser) {
		return "registration-success";
	}

	@GetMapping("/creer-annonce")
	public String CreateAnnoncePage(@ModelAttribute("annonce") Annonce theAnnonce) {
		return "saveAnnonce";
	}

	@PostMapping("/saveAnnonce")
	public String saveAnnonce(@ModelAttribute("annonce") Annonce theAnnonce) throws MalformedURLException, JsonProcessingException {
		presentation.saveAnnonce(theAnnonce);
		int userId=presentation.getCurrentUserId();
		int newId= presentation.lastAnnonceId();
		presentation.saveTrajet(presentation.createTrajet(userId,newId,userId,3));
		return "redirect:/home";
	}


	@GetMapping("/liste-annonce")
	public String returnListOfAnnonces(Model theModel) throws MalformedURLException, JsonProcessingException {
		int userId= presentation.getCurrentUserId();
		List<Annonce> theAnnonces=  presentation.findAllAnnonces();
		List<Integer> lastStatus= new ArrayList<>();
		presentation.listeAnnonceAffichage(theAnnonces,lastStatus);
		// add to the spring model
		theModel.addAttribute("annonces", theAnnonces);
		theModel.addAttribute("statusList", lastStatus);
		return "liste-annonce";
	}

	@GetMapping("/accepte-annonce")
	public String AccepterAnnonce(@ModelAttribute("annonce_id") int annonce_id, Model theModel) throws MalformedURLException, JsonProcessingException {
		presentation.accepteAnnonce(annonce_id);
		return "redirect:/liste-annonce";
	}

	@GetMapping("/liste-trajets-user")
	public String returnListOfTrajets(Model theModel) throws MalformedURLException, JsonProcessingException {
		List<Annonce> theAnnonces= new ArrayList<>();
		List<Integer> lesStatus = new ArrayList<>();
		presentation.ListeDesTrajets(theAnnonces,lesStatus);
		// add to the spring model
		theModel.addAttribute("annonces", theAnnonces);
		theModel.addAttribute("statusList", lesStatus);
		return "liste-trajets-user";
	}

	@GetMapping("/liste-proposition")
	public String returnListOfPropositions(Model theModel) throws MalformedURLException, JsonProcessingException {
		List<Annonce> theAnnonces = new ArrayList<>();
		List<String> theUsersNomPrenom = new ArrayList<>();
		List<Integer> proposedUserId = new ArrayList<>();
		List<Integer> reponseConducteur = new ArrayList<>();
		presentation.listeDesPropositions(theAnnonces,theUsersNomPrenom,proposedUserId,reponseConducteur);
		theModel.addAttribute("annonces", theAnnonces);
		theModel.addAttribute("usersNomPrenom", theUsersNomPrenom);
		theModel.addAttribute("proposedUserId", proposedUserId);
		theModel.addAttribute("reponseConducteur", reponseConducteur);
		return "liste-proposition";
	}

	@GetMapping("/accepte-proposition/{id1}/{id2}")
	public String RefuserProposition(@ModelAttribute("id1") int userIdPropose,@ModelAttribute("id2") int annonceId, Model theModel) throws MalformedURLException, JsonProcessingException {
			presentation.accepteProposition( userIdPropose, annonceId);
			return "redirect:/home";
	}

	@GetMapping("/refuse-proposition/{id1}/{id2}")
	public String AccepterProposition(@ModelAttribute("id1") int userIdPropose,@ModelAttribute("id2") int annonceId, Model theModel) throws MalformedURLException, JsonProcessingException {
		presentation.refuseProposition( userIdPropose, annonceId);
		return "redirect:/home";
	}

	@GetMapping("/details")
	public String DetailsTrajet(@ModelAttribute("annonce_id") int annonceId, Model theModel) throws MalformedURLException, JsonProcessingException {
		List<User> userList =new ArrayList<User>();
		presentation.details(userList,annonceId);
		theModel.addAttribute("userList", userList);
		return "details";
		}

	@GetMapping("/details-annonce")
	public String DetailsAnnonce(@ModelAttribute("annonce_id") int annonceId, Model theModel) throws MalformedURLException, JsonProcessingException {
		Annonce theAnnonce = presentation.findByIdAnnonce(annonceId);
		String link=presentation.getAnnonceDetails(theAnnonce);;
		theModel.addAttribute("annonce", theAnnonce);
		theModel.addAttribute("link", link);
		return "details-annonce";
	}
}
