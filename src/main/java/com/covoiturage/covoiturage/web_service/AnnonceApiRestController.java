package com.covoiturage.covoiturage.web_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/annonceAPI")
public class AnnonceApiRestController {

    API_EXT_INE apiMethod=new API_EXT_INE();
    @PostMapping(value = "/createAnnonce", consumes = "application/json", produces = "application/json")
    public String createAnnonceAPI(@RequestBody String reponse) throws JsonProcessingException, MalformedURLException, ParseException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();// Pour que jackson comprends le LocalDateTime
        Map<String,Object> map = mapper.readValue(reponse,  HashMap.class);
        int userId = (int) map.get("userId");
        LocalDateTime date= LocalDateTime.parse(map.get("date").toString());
        String depart= (String) map.get("depart");
        String arrive= (String) map.get("arrive");
        String commentaire= (String) map.get("commentaire");
        jsonObject.put("userId",userId);
        jsonObject.put("date",date);
        jsonObject.put("depart",depart);
        jsonObject.put("arrive",arrive);
        jsonObject.put("commentaire",commentaire);
        
        List<Double> list= apiMethod.coordinateAPI(depart,arrive);
        double latitude1= list.get(0);
        double longitude1= list.get(1);
        double latitude2= list.get(2);
        double longitude2= list.get(3);

        jsonObject.put("latitude1",latitude1);
        jsonObject.put("longitude1",longitude1);
        jsonObject.put("latitude2",latitude2);
        jsonObject.put("longitude2",longitude2);
        String duree=apiMethod.dureeAPI(latitude1,longitude1,latitude2,longitude2);
        jsonObject.put("duree",duree);
        String createPersonUrl = "http://localhost:8090/dao/saveAnnonce";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();// Pour que jackson comprends le LocalDateTime
        HttpEntity<String> request =
                new HttpEntity<String>(jsonObject.toString(), headers);
        String personResultAsJsonStr =
                restTemplate.postForObject(createPersonUrl, request, String.class);//C'est ça qui post
        RestTemplate restTemplateTrajet = new RestTemplate();
        HttpHeaders headersTrajet = new HttpHeaders();
        headersTrajet.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObjectTrajet = new JSONObject();
        ObjectMapper mapperTrajet = new ObjectMapper();
        jsonObjectTrajet.put("userId",userId);
        int size=this.sizeAnnonce();
        jsonObjectTrajet.put("annonceId",size);
        jsonObjectTrajet.put("estAccepte",3);
        jsonObjectTrajet.put("conducteurId",userId);
        String createUrl = "http://localhost:8090/dao/saveTrajet";
        HttpEntity<String> requestTrajet =
                new HttpEntity<String>(jsonObjectTrajet.toString(), headers);
        String reponseTrajet =
                restTemplateTrajet.postForObject(createUrl, requestTrajet, String.class);//C'est ça qui post
        return jsonObject.toString();
    }

    public int sizeAnnonce(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String createPersonUrl = "http://localhost:8090/services/sizeAnnonce";
        HttpEntity<String> request = new HttpEntity<String>( headers);
        ResponseEntity<HashMap> result = restTemplate.exchange(createPersonUrl, HttpMethod.GET, request, HashMap.class);
        Map<String,Integer> x=result.getBody();
        return x.get("size");
    }
}