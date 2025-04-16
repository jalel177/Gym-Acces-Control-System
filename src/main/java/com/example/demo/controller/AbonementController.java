package com.example.demo.controller;

import com.example.demo.model.Abonement;
import com.example.demo.model.User;
import com.example.demo.service.Abonementinterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class AbonementController {
    @Autowired
    Abonementinterface abonementinterface;

    @PostMapping("addabo")
    @Transactional
    public Abonement addAbo(@RequestBody Abonement abonement)
    {

        return abonementinterface.addabo(abonement);
    }
    @DeleteMapping("/deleteabo")
    public void deleteAbonnement(@RequestParam Long id){
        abonementinterface.deleteabo(id);
    }
    @PostMapping("addlistabo")
    public List<Abonement> addlistabonnement(@RequestBody List<Abonement> abonement)
    {
        return abonementinterface.addListabonnement(abonement);
    }
    @PatchMapping("/updateabo/{ids}")
    public Abonement updateabo(@PathVariable("ids")Long id,@RequestBody Abonement abonement)
    {return abonementinterface.updateabonnement(id,abonement);}

    @GetMapping("/getAllabo")
    public   List<Abonement> getAllabo()
    {
        return abonementinterface.getAllabonement();
    }

    @GetMapping("getAboByTYPE/{ty}")
    public Abonement getAbobytype(@PathVariable String ty)
    {
        return abonementinterface.getabonementsBytype(ty);
    }

    @GetMapping("getAbosByType/{ty}")
    public   List<Abonement> getAbosByType(@PathVariable String ty)
    {
        return abonementinterface.getAllabonementbytype(ty);
    }
    @GetMapping("getAbosBydatedebuts/{debuts}")
    public   List<Abonement> getAbosBydatedebuts(@PathVariable LocalDate debuts)
    {
        return abonementinterface.getAllabonementbydatedebuts(debuts);
    }
    @GetMapping("getAboBydebut/{debut}")
    public Abonement getAbobydebut(@PathVariable LocalDate debut)
    {
        return abonementinterface.getabonementsBydatedebut(debut);
    }
    @GetMapping("getAbosBydatefins/{fins}")
    public   List<Abonement> getAbosBydatefins(@PathVariable LocalDate fins)
    {
        return abonementinterface.getAllabonementbydatefins(fins);
    }
    @GetMapping("getAboByfin/{fin}")
    public Abonement getAbobydatefin(@PathVariable LocalDate fin)
    {
        return abonementinterface.getabonementsBydatefin(fin);
    }



}
