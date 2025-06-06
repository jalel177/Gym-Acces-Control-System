package com.example.demo.controller;

import com.example.demo.model.Abonement;
import com.example.demo.model.SeanceCours;
import com.example.demo.service.Seanceinterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("seancecours")
public class SeanceCoursController {
    @Autowired
    private Seanceinterface seanceinterface;
    @PostMapping("/addseance")
    @Transactional
    public SeanceCours addseance(@RequestBody SeanceCours seancecour)
    {
        return seanceinterface.addSeance(seancecour);}

    @PostMapping("/addlistseances")
    public List<SeanceCours> addlistSeances(@RequestBody List<SeanceCours> seancecours)
    {
        return seanceinterface.addListseancecours(seancecours);}

    @DeleteMapping("/deleteseance")
    public ResponseEntity<?> deleteSeance(@RequestParam Long coursid) {
        try {
            seanceinterface.deletseance(coursid);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("error", e.getReason()));
        }
    }
    @Transactional
    @PutMapping("/updateseance/{ids}")
    public SeanceCours updateseance(@PathVariable("ids")Long coursid,@RequestBody SeanceCours seancecour)
    {return seanceinterface.updateseance(coursid,seancecour);}

    @GetMapping("/getallseances")
    public   List<SeanceCours> getAllseances()
    {
        return seanceinterface.getAllseances();
    }
    @GetMapping("getSeanceByEntraineur/{en}")
    public SeanceCours getSeancebyEntraineur(@PathVariable String en)
    {
        return seanceinterface.getseancebyentraineur(en);
    }
    @GetMapping("/getSeancesByEntraineurs/{ens}")
    public List <SeanceCours> getSeancesbyEntraineurs(@PathVariable String ens)
    {
        return seanceinterface.getAllseancesbyentraineurs(ens);
    }
    @GetMapping("/getSeancesByDates/{da}")
    public SeanceCours getSeancebyDate(@PathVariable String da)
    {
        return seanceinterface.getseancebydate(da);
    }
    @GetMapping("/getSeancesByDates/{das}")
    public List<SeanceCours> getSeancesbyDates(@PathVariable String das)
    {
        return seanceinterface.getAllseancesbydates(das);
    }


}
