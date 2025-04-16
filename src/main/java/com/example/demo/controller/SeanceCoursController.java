package com.example.demo.controller;

import com.example.demo.model.Abonement;
import com.example.demo.model.SeanceCours;
import com.example.demo.service.Seanceinterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/addseances")
    public List<SeanceCours> addlistSeances(@RequestBody List<SeanceCours> seancecours)
    {
        return seanceinterface.addListseancecours(seancecours);}

    @DeleteMapping("/deleteseance")
    public void deleteSeance(@RequestParam Long id){seanceinterface.deletseance(id);
    }
    @PatchMapping("/updateseance/{ids}")
    public SeanceCours updateseance(@PathVariable("ids")Long id,@RequestBody SeanceCours seancecour)
    {return seanceinterface.updateseance(id,seancecour);}

    @GetMapping("/getAllseances")
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
