package com.covoiturage.covoiturage.web_service;

import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.Trajet;
import com.covoiturage.covoiturage.entity.User;
import com.covoiturage.covoiturage.repository.AnnonceRepository;
import com.covoiturage.covoiturage.repository.TrajetRepository;
import com.covoiturage.covoiturage.repository.UserRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webservice")
public class WebServiceRestController {
    API_EXT_INE apiMethod=new API_EXT_INE();

    @PostMapping(value = "/coordinates", consumes = "application/json", produces = "application/json")
    public String coordApiService(@RequestBody String reponse) throws JsonProcessingException, MalformedURLException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = mapper.readValue(reponse,  HashMap.class);
        String depart=map.get("depart");
        String arrive=map.get("arrive");

        List<Double> list=apiMethod.coordinateAPI(depart,arrive);
        JSONObject coordJson = new JSONObject();
        coordJson.put("latitude1",list.get(0));
        coordJson.put("longitude1",list.get(1));
        coordJson.put("latitude2",list.get(2));
        coordJson.put("longitude2",list.get(3));
        return coordJson.toString();
    }

    @PostMapping(value = "/duree", consumes = "application/json", produces = "application/json")
    public String dureeApiService(@RequestBody String reponse) throws JsonProcessingException, MalformedURLException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Double> map = mapper.readValue(reponse,  HashMap.class);
        double latitude1 =map.get("latitude1");
        double longitude1=map.get("longitude1");
        double latitude2=map.get("latitude2");
        double longitude2=map.get("longitude2");
        String duree=apiMethod.dureeAPI(latitude1,longitude1,latitude2,longitude2);
       JSONObject dureeJson = new JSONObject();
        dureeJson.put("duree",duree);
        return dureeJson.toString();
    }
}