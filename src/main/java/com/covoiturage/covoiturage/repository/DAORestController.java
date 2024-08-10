package com.covoiturage.covoiturage.repository;

import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.Trajet;
import com.covoiturage.covoiturage.entity.User;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dao")
public class DAORestController {
    private UserRepositoryImpl userRepositoryImpl;
    private AnnonceRepository annonceRepository;
    private TrajetRepository trajetRepository;
    public DAORestController(UserRepositoryImpl ur,TrajetRepository tr,AnnonceRepository ar) {
        this.userRepositoryImpl = ur;
        this.trajetRepository = tr;
        this.annonceRepository=ar;
    }

    @GetMapping("/users")
    public List<User> findAll() {

        return userRepositoryImpl.findAll();
    }

    @GetMapping("/user/{id}")
    public User findByIdUser(@ModelAttribute("id") int id) {
        User user = userRepositoryImpl.findById(id).get();
        return user;
    }

    @GetMapping("/userName/{firstname}")
    public User findByFirstName(@ModelAttribute("firstname") String firstname) {
        User user = userRepositoryImpl.findByFirstName(firstname);
        return user;
    }

    @PostMapping(value = "/saveUser", consumes = "application/json", produces = "application/json")
    public void saveUser(@RequestBody User user) {

        userRepositoryImpl.save(user);
    }

    @GetMapping("/trajets")
    public List<Trajet> findAllTrajet() {

        return trajetRepository.findAll();
    }

    @GetMapping("/trajet/{id}")
    public Trajet findByIdTrajet(@ModelAttribute("id") int id) {
        Trajet trajet = trajetRepository.findById(id).get();
        return trajet;
    }

    @PostMapping(value = "/saveTrajet", consumes = "application/json", produces = "application/json")
    public void saveTrajet(@RequestBody Trajet trajet) {

        trajetRepository.save(trajet);
    }

    @GetMapping("/annonces")
    public List<Annonce> findAllAnnonce() {

        return annonceRepository.findAll();
    }

    @GetMapping("/annonce/{id}")
    public Annonce findByIdAnnonce(@ModelAttribute("id") int id) {
        Annonce annonce = annonceRepository.findById(id).get();
        return annonce;
    }

    @PostMapping(value = "/saveAnnonce", consumes = "application/json", produces = "application/json")
    public void saveAnnonce(@RequestBody Annonce annonce) {

        annonceRepository.save(annonce);
    }
}