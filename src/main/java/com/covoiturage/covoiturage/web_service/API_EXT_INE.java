package com.covoiturage.covoiturage.web_service;

import com.covoiturage.covoiturage.controller.UserController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class API_EXT_INE {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    public  String getJson(URL url) {
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

    public List<Integer> getListUsersAPI() throws MalformedURLException, JsonProcessingException {
        URL url=new URL("https://c363cd03-adfa-4394-8a07-3a0a269acdf5.mock.pstmn.io/numeroEtudiant");
        String EtudiantsAPI=this.getJson(url);
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> listUsersAPI = Arrays.asList(mapper.readValue(EtudiantsAPI, Integer[].class));
        return listUsersAPI;
    }

    public  List<Double> coordinateAPI(String origine, String destionation) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("https://fr.distance24.org/route.json?stops="+origine+"|"+destionation+"");
        String reponse= this.getJson(url);
        Map<String, ArrayList<Map<String,Double>>>  map = new ObjectMapper().readValue(reponse, HashMap.class);
        double latutude1=map.get("stops").get(0).get("latitude");
        double longitude1=map.get("stops").get(0).get("longitude");
        double latutude2=map.get("stops").get(1).get("latitude");
        double longitude2=map.get("stops").get(1).get("longitude");
        List<Double> coords= new ArrayList<>();
        coords.add(latutude1);
        coords.add(longitude1);
        coords.add(latutude2);
        coords.add(longitude2);
        return coords;
    }
    
     /*public  List<Double> coordinateAPI(String origine, String destionation) throws MalformedURLException, JsonProcessingException {
    	 
    	//Mise en place de RestTemplate pour la gestion des appels aux API
     	RestTemplate restTemplate = new RestTemplate();
     	
     	//PARTIE 1 : API ADRESSE ETALAB GESTION
         String etalabApiUrl = "https://api-adresse.data.gouv.fr/search/?q=" + addressForm.getAddress();                                     
         //EtalabAdresseResponse etalabResponse = restTemplate.getForObject(etalabApiUrl, EtalabAdresseResponse.class);
         //HttpEntity<String> request = new HttpEntity<String>();
         ResponseEntity<HashMap> result = restTemplate.getForObject(etalabApiUrl, HashMap.class);
         Map<String,ArrayList<ArrayList<Map<String,Map<String,String>>>>> list2=result.getBody();
         //Récupérez les coordonnées depuis la réponse d'Etalab          	       	
         double longitude = etalabResponse.getFeatures().get(0).getGeometry().getCoordinates().get(0);      
         double latitude = etalabResponse.getFeatures().get(0).getGeometry().getCoordinates().get(1);
         
         // Affichez les informations de ETALAB recpéré dans la console
         System.out.println("\nCity: " + city);
         System.out.println("latitude: " + latitude);
         System.out.println("longitude: " + longitude);
    	 
     }*/

    /*public  List<Double> coordinateAPI(String origine, String destionation) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("https://api-adresse.data.gouv.fr/search/?q="+origine);
        URL url1=new URL("https://api-adresse.data.gouv.fr/search/?q="+destionation);

        String reponse= this.getJson(url);
        String reponse1= this.getJson(url1);
        System.out.println(reponse);
        Map<String, ArrayList<ArrayList<Map<String,Map<String,Map<ArrayList, Double>>>>>>  map = new ObjectMapper().readValue(reponse1, HashMap.class);
        Map<String, ArrayList<ArrayList<Map<String,Map<String,Map<ArrayList, Double>>>>>> map1 = new ObjectMapper().readValue(reponse1, HashMap.class);
        double latutude1=map.get("features").get(0).get(0).get("geometry").get("coordinates").get(0);
        double longitude1=map.get("features").get(0).get(0).get("geometry").get("coordinates").get(1);
        double latutude2=map1.get("features").get(0).get(0).get("geometry").get("coordinates").get(0);
        double longitude2=map1.get("features").get(0).get(0).get("geometry").get("coordinates").get(1);
        List<Double> coords= new ArrayList<>();
        coords.add(latutude1);
        coords.add(longitude1);
        coords.add(latutude2);
        coords.add(longitude2);
        return coords;
    }*/
    
/*
    public List<Double> coordinateAPI(String origine, String destination) throws Exception {
        String apiUrl = "https://api-adresse.data.gouv.fr/search/?q=";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity1 = restTemplate.exchange(apiUrl + origine, HttpMethod.GET, null, String.class);
        ResponseEntity<String> responseEntity2 = restTemplate.exchange(apiUrl + destination, HttpMethod.GET, null, String.class);
        String reponse1 = responseEntity1.getBody();
        String reponse2 = responseEntity2.getBody();
        System.out.println(reponse1);
        System.out.println(reponse2);
        Map<String, Object> map1 = new ObjectMapper().readValue(reponse1, Map.class);
        Map<String, Object> map2 = new ObjectMapper().readValue(reponse2, Map.class);
        List<Double> coords1 = extractCoordinates(map1);
        List<Double> coords2 = extractCoordinates(map2);
        List<Double> coords = new ArrayList<>();
        coords.addAll(coords1);
        coords.addAll(coords2);
        return coords;
    }

    private List<Double> extractCoordinates(Map<String, Object> map) {
        double latitude = ((List<Double>) ((List<Object>) ((Map<String, Object>) ((List<Object>) map.get("features")).get(0)).get("geometry")).get(0)).get(1);
        double longitude = ((List<Double>) ((List<Object>) ((Map<String, Object>) ((List<Object>) map.get("features")).get(0)).get("geometry")).get(0)).get(0);
        System.out.println(latitude);
        System.out.println(longitude);
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(latitude);
        coordinates.add(longitude);
        return coordinates;
    }*/
        
    public String dureeAPI(double latitude1,double longitude1, double latitude2,double longitude2) throws JsonProcessingException {
    	//https://api.radar.io/v1/route/matrix?origins=49.119666,6.176905&destinations=49.470163,5.930146&mode=car&units=imperial
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.add("Authorization","prj_live_pk_a3274ce3fa36801a7bda77159ad771de9536cd0a");
        //personJsonObject.put("userId",annonce.getUserId());
        headers.add("Authorization","prj_live_pk_280ee05fafb20726b59d20ca99f945c6d6a4c2d9");
        String createPersonUrl = "https://api.radar.io/v1/route/matrix?origins="+latitude1+","+longitude1+"&destinations="+latitude2+","+longitude2+"&mode=car&units=metric";
        HttpEntity<String> request = new HttpEntity<String>(  headers);
        //String personResultAsJsonStr =restTemplate.exchange(createPersonUrl, request, String.class);//C'est ça qui post
        ResponseEntity<HashMap> result = restTemplate.exchange(createPersonUrl, HttpMethod.GET, request, HashMap.class);
        Map<String,ArrayList<ArrayList<Map<String,Map<String,String>>>>> list2=result.getBody();
        String duree=list2.get("matrix").get(0).get(0).get("duration").get("text");
        System.out.println(duree+"\nheure est arrivée lhhihihihigigig\n");
        return  duree;
    }
}

//https://api.radar.io/v1/route/matrix?origins=49.119666,6.176905&destinations=49.470163,5.930146&mode=car&units=imperial
//AIzaSyBN4_F3cBbadQ4x1PqZf6_OCktum1dmkJg
//https://fr.distance24.org/route.json?stops=Metz|Villerupt



