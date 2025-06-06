package com.example.demo.controller;

import com.example.demo.model.Favoris;
import com.example.demo.model.User;
import com.example.demo.service.Favorisinterface;
import com.example.demo.service.Keycloakserviceimplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("favoris")
public class FavorisController {
    @Autowired
    private Favorisinterface favorisinterface;

    @Autowired
    private Keycloakserviceimplement keycloakUserService;

    @PostMapping("/addfavoris/{userid}/{coursid}")
    public ResponseEntity<Favoris> addToFavoris(
            @PathVariable String userid,
            @PathVariable Long coursid
    ) {
        Favoris favoris = favorisinterface.addToFavoris(userid, coursid);
        return new ResponseEntity<>(favoris, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletefavoris/{userid}/{coursid}")
    public ResponseEntity<Void> removeFromFavoris(
            @PathVariable String userid,
            @PathVariable Long coursid
    ) {
        favorisinterface.removeFromFavoris(userid, coursid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getmyfavoris/{userid}")
    public ResponseEntity<List<Favoris>> getMyFavoris(@PathVariable("userid") String userid) {
        List<Favoris> favoris = favorisinterface.getFavorisByUser(userid);
        return ResponseEntity.ok(favoris);
    }


}


