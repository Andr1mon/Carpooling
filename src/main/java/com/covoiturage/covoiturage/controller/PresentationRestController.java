package com.covoiturage.covoiturage.controller;

import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.Trajet;
import com.covoiturage.covoiturage.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/present")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PresentationRestController {
@Autowired
    @Qualifier("cont")
    private UserController cont;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    static int errorLoginv=0;

    public static String getApi(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users")
    public List<User> findAllUsers() throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/services/users");
        String usersAPI=this.getApi(url);
        ObjectMapper mapper = new ObjectMapper();
        List<User> listUsersAPI = Arrays.asList(mapper.readValue(usersAPI, User[].class));
        return listUsersAPI;
    }

    @GetMapping("/user")
    public User findByIdUser(int id) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/services/userPost/"+id);
        String usersAPI=this.getApi(url);
        ObjectMapper mapper = new ObjectMapper();
        User UsersAPI = mapper.readValue(usersAPI, User.class);
        return UsersAPI;
    }

    @GetMapping("/userByName")
    public User findByFirstNameUser(String name) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/services/userByNamePost/"+name);
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        User UsersAPI = mapper.readValue(usersAPI, User.class);
        return UsersAPI;
    }

    @GetMapping(value = "/saveUser", produces = "application/json")
    public void saveUser(User user,String value) throws JsonProcessingException {
        if (value != null) {
            user.setPassword(value);
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("firstName",user.getFirstName());
        personJsonObject.put("lastName",user.getLastName());
        personJsonObject.put("ine",user.getIne());
        personJsonObject.put("email",user.getEmail());
        personJsonObject.put("password",user.getPassword());
        String createPersonUrl = "http://localhost:8090/services/saveUserPost";
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> request =
                new HttpEntity<String>(personJsonObject.toString(), headers);
        String personResultAsJsonStr =
                restTemplate.postForObject(createPersonUrl, request, String.class);//C'est ça qui post
    }

    @GetMapping("/trajets")
    public List<Trajet> findAllTrajets() throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/services/trajets");
        String usersAPI=this.getApi(url);
        ObjectMapper mapper = new ObjectMapper();
        List<Trajet> listTrajetsAPI = Arrays.asList(mapper.readValue(usersAPI, Trajet[].class));
        return listTrajetsAPI;
    }

    @GetMapping("/trajet")
    public Trajet findByIdTrajet(int id) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/services/trajetPost/"+id);
        String trajetAPI=this.getApi(url);
        ObjectMapper mapper = new ObjectMapper();
        Trajet trajet = mapper.readValue(trajetAPI, Trajet.class);
        return trajet;
    }

    @GetMapping(value = "/saveTrajet", produces = "application/json")
    public Trajet saveTrajet(Trajet trajet) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        if(trajet.getId()>0) {
            personJsonObject.put("id", trajet.getId());
        }
        personJsonObject.put("userId",trajet.getUserId());
        personJsonObject.put("annonceId",trajet.getAnnonceId());
        personJsonObject.put("estAccepte",trajet.getEstAccepte());
        personJsonObject.put("conducteurId",trajet.getConducteurId());
        String createPersonUrl = "http://localhost:8090/services/saveTrajetPost";
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> request =
                new HttpEntity<String>(personJsonObject.toString(), headers);
        String personResultAsJsonStr =
                restTemplate.postForObject(createPersonUrl, request, String.class);//C'est ça qui post
        return trajet;
    }

    @GetMapping("/annonces")
    public List<Annonce> findAllAnnonces() throws MalformedURLException, JsonProcessingException {

        URL url=new URL("http://localhost:8090/services/annonces");
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();// Pour que jackson comprends le LocalDateTime

        List<Annonce> listAnnoncesAPI = Arrays.asList(mapper.readValue(usersAPI, Annonce[].class));

        return listAnnoncesAPI;
    }

    @GetMapping("/annonce")
    public Annonce findByIdAnnonce(int id) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/services/annoncePost/"+id);
        String annonceAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();// Pour que jackson comprends le LocalDateTime

        Annonce annonce = mapper.readValue(annonceAPI, Annonce.class);
        return annonce;
    }

    public int getCurrentUserId() throws MalformedURLException, JsonProcessingException {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();
        User user = this.findByFirstNameUser(login);
        return  user.getId();
    }
    public int lastAnnonceId() throws MalformedURLException, JsonProcessingException {
        List<Annonce> theAnnonces=  this.findAllAnnonces();

        int newId=theAnnonces.get(theAnnonces.size()-1).getId();
        return newId;
    }

    public Trajet createTrajet(int userId,int newId,int userId2,int status){
        Trajet tr = new Trajet(userId, newId, userId2, status);
        return  tr;
    }

    public void listeAnnonceAffichage(List<Annonce> theAnnonces,List<Integer> lastStatus) throws MalformedURLException, JsonProcessingException {
        List<Trajet> trAll=  this.findAllTrajets();
        List<Integer> annonceIds= new ArrayList<>();
        List<Integer> lesStatus= new ArrayList<>();
        for (int i = 0; i < trAll.size(); i++) {
            //si utilisateur a choisi cette annonec
            if(trAll.get(i).getUserId()==this.getCurrentUserId()){
                annonceIds.add(trAll.get(i).getAnnonceId());
                lesStatus.add(trAll.get(i).getEstAccepte());
                //0-> rien, 1->accepté, 2-> refusé, 3->vous êtes le conducteur 4-> demande envoyée
            }
            else {
                annonceIds.add(-1);
                lesStatus.add(0);
            }
        }
        for (int i = 0; i < theAnnonces.size(); i++) {
            boolean trouve=false;
            for (int j = 0; j < annonceIds.size(); j++) {
                if (theAnnonces.get(i).getId() ==annonceIds.get(j) && trouve==false){
                    lastStatus.add(lesStatus.get(j));
                    trouve=true;
                }
            }
            if(trouve==false){
                lastStatus.add(0);
            }
        }
    }

    public void accepteAnnonce(int annonce_id) throws MalformedURLException, JsonProcessingException {
        Annonce theAnnonce = this.findByIdAnnonce(annonce_id);
        int userId= this.getCurrentUserId();
        if(theAnnonce.getUserId()==userId) {
            this.saveTrajet(this.createTrajet(userId,annonce_id,theAnnonce.getUserId(),3));
        }
        else {
            Trajet tr=new Trajet();
            this.saveTrajet( this.createTrajet(userId,annonce_id,theAnnonce.getUserId(),4));
        }
    }

public void ListeDesTrajets(List<Annonce> theAnnonces,List<Integer> lesStatus) throws MalformedURLException, JsonProcessingException {
    List<Trajet> theTrajets = this.findAllTrajets();
    int userId= this.getCurrentUserId();
    List<Integer> theAnnoncesId = new ArrayList<>();
    for (int i = 0; i < theTrajets.size(); i++) {
        if (theTrajets.get(i).getUserId() == userId) {
            theAnnoncesId.add(theTrajets.get(i).getAnnonceId());
            lesStatus.add(theTrajets.get(i).getEstAccepte());
        }
    }
    for (int i = 0; i < theAnnoncesId.size(); i++) {
        theAnnonces.add(this.findByIdAnnonce(theAnnoncesId.get(i)));
    }
}
    List<Annonce> theAnnonces = new ArrayList<>();
    List<String> theUsersNomPrenom = new ArrayList<>();
    List<Integer> proposedUserId = new ArrayList<>();
    List<Integer> reponseConducteur = new ArrayList<>();

public void listeDesPropositions( List<Annonce> theAnnonces,List<String> theUsersNomPrenom,List<Integer> 
	proposedUserId,List<Integer> reponseConducteur) throws MalformedURLException, JsonProcessingException {
    List<Trajet> theTrajets = this.findAllTrajets();
    int userId= this.getCurrentUserId();
    for (int i = 0; i < theTrajets.size(); i++) {

        if (theTrajets.get(i).getConducteurId() == userId) {
            theAnnonces.add(this.findByIdAnnonce(theTrajets.get(i).getAnnonceId()));
            theUsersNomPrenom.add(this.findByIdUser(theTrajets.get(i).getUserId()).getFirstName()+ " "
            +this.findByIdUser(theTrajets.get(i).getUserId()).getLastName());
            proposedUserId.add(theTrajets.get(i).getUserId());
            if (theTrajets.get(i).getEstAccepte()==1) {
                reponseConducteur.add(1);
            }
            else if (theTrajets.get(i).getEstAccepte()==2){
                reponseConducteur.add(2);
            }
            else if (theTrajets.get(i).getEstAccepte()==3){
                reponseConducteur.add(3);
            }
            else {
                reponseConducteur.add(0);
            }
        }
    }
}

public void accepteProposition(int userIdPropose, int annonceId) throws MalformedURLException, JsonProcessingException {
    List<Trajet> theTrajets = this.findAllTrajets();
    for (int i = 0; i < theTrajets.size(); i++) {
        if ((theTrajets.get(i).getAnnonceId() == annonceId)&&(theTrajets.get(i).getUserId()==userIdPropose)) {
            Trajet trajetPropose=this.findByIdTrajet(theTrajets.get(i).getId());
            trajetPropose.setEstAccepte(1);
            this.saveTrajet(trajetPropose);
        }
    }
}

    public void refuseProposition(int userIdPropose, int annonceId) throws MalformedURLException, JsonProcessingException {
        List<Trajet> theTrajets = this.findAllTrajets();
        for (int i = 0; i < theTrajets.size(); i++) {
            if ((theTrajets.get(i).getAnnonceId() == annonceId)&&(theTrajets.get(i).getUserId()==userIdPropose)) {
                Trajet trajetPropose=this.findByIdTrajet(theTrajets.get(i).getId());
                trajetPropose.setEstAccepte(2);
                this.saveTrajet(trajetPropose);
            }
        }
    }

    public void details(List<User> userList,int annonceId) throws MalformedURLException, JsonProcessingException {
        List<Integer> userIds= new ArrayList<>();
        List<Trajet> theTrajets = this.findAllTrajets();
        for (int i = 0; i < theTrajets.size(); i++) {
            if((theTrajets.get(i).getAnnonceId()==annonceId)&&(theTrajets.get(i).getEstAccepte() 
            		!=0)&&(theTrajets.get(i).getEstAccepte() !=2)&&(theTrajets.get(i).getEstAccepte() !=4)){
                userIds.add(theTrajets.get(i).getUserId());
            }
        }
        logger.info("FFFFFFFFFFFEFEFEFE"+userIds.size());
        for (int i = 0; i < userIds.size(); i++) {
            userList.add(this.findByIdUser(userIds.get(i)));
        }
    }

        @GetMapping(value = "/saveAnnonce", produces = "application/json")
    public Annonce saveAnnonce(Annonce annonce) throws JsonProcessingException, MalformedURLException {
        int userId= this.getCurrentUserId();
        annonce.setUserId(userId);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("userId",annonce.getUserId());
        String new_date= String.valueOf(annonce.getDate());
        new_date = new_date.replace(" ", "T");
        personJsonObject.put("date",new_date);
        personJsonObject.put("depart",annonce.getDepart());
        personJsonObject.put("arrive",annonce.getArrive());
        personJsonObject.put("commentaire",annonce.getCommentaire());
        String createPersonUrl = "http://localhost:8090/services/saveAnnoncePost";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();// Pour que jackson comprends le LocalDateTime
        HttpEntity<String> request =
        new HttpEntity<String>(personJsonObject.toString(), headers);
        String personResultAsJsonStr = restTemplate.postForObject(createPersonUrl, request, String.class);
        //C'est ça qui post
        return annonce;
    }

    @PostMapping(value = "/errorLogin", consumes = "application/json")
    public void errorLogin(@RequestBody String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Integer> participantJsonList = mapper.readValue(response,  HashMap.class);
        errorLoginv=1;
        logger.info("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ"+participantJsonList.get("error"));
    }
     
    public String getAnnonceDetails(Annonce theAnnonce) throws MalformedURLException, JsonProcessingException {
        String link="\t<iframe src=\"https://www.google.com/maps/embed/v1/directions?key=AIzaSyBN4_F3cBbadQ4x1PqZf6_OCktum1dmkJg&origin="+theAnnonce.getDepart()+"&destination="+theAnnonce.getArrive()+"&avoid=tolls|highways\" width=\"400\" height=\"350\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>\n";
        return  link;
   }
}
