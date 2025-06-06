package com.example.demo.service;

import com.example.demo.dao.CommentaireRepository;
import com.example.demo.dao.SeancecourRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.SeanceCours;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class SeanceServiceimplement implements  Seanceinterface {
    @Autowired
    private SeancecourRepository seancecourrepo;
    @Autowired
    private CommentaireRepository comrepo;
    @Autowired
    private UserRepository userreop;



    private final EntityManager entityManager;

    public SeanceServiceimplement(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SeanceCours addSeance(SeanceCours seanceCours) {
        return seancecourrepo.save(seanceCours);
    }

    @Override
    public void deletseance(Long coursid) {
        try {
            entityManager.clear();

            // 1. Delete in reverse dependency order
            // Delete child records first
            seancecourrepo.deleteByCoursId(coursid);  // seance_com
            comrepo.deleteBySeanceCoursIdNative(coursid); // comments
            userreop.clearSessionRelationships(coursid); // user relationships

            // 2. Delete parent
            seancecourrepo.deleteById(coursid); // Use JPA method

            entityManager.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found");
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Deletion failed: " + e.getMessage()
            );
        }
    }
    @Override
    public List<SeanceCours> addListseancecours(List<SeanceCours> listseances) {
        return seancecourrepo.saveAll(listseances);

    }
    @Override
    public List<SeanceCours> getAllseances() {
        return seancecourrepo.findAll();
    }

    @Override
    public SeanceCours getseance(Long id) {
        return seancecourrepo.findById(id).orElse(null);
    }
    @Override
    public SeanceCours updateseance(Long coursid, SeanceCours seancecours) {
        SeanceCours existingSeance = seancecourrepo.findById(coursid)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id: " + coursid));


        if (seancecours.getDate() != null) {
            existingSeance.setDate(seancecours.getDate());
        }
        if (seancecours.getEntraineur() != null) {
            existingSeance.setEntraineur(seancecours.getEntraineur());
        }
        if (seancecours.getDuration() != null) {
            existingSeance.setDuration(seancecours.getDuration());
        }
        if (seancecours.getSportname() != null) {
            existingSeance.setSportname(seancecours.getSportname());
        }

        // 3. Save and return the updated entity
        return seancecourrepo.save(existingSeance);
    }

    @Override
    public SeanceCours getseancebyentraineur(String en) {
        return seancecourrepo.findSeanceCoursByEntraineur(en);
    }

    @Override
    public List<SeanceCours> getAllseancesbyentraineurs(String en) {
        return seancecourrepo.findSeanceCoursByEntraineurs(en);
    }
    @Override
    public List<SeanceCours> getAllseancesbysportsname(String sp) {
        return seancecourrepo.findSeanceCoursBySportname(sp);
    }
    @Override
    public SeanceCours getseancebydate(String da) {
        return seancecourrepo.findSeanceCoursByDate(da);
    }

    @Override
    public List<SeanceCours> getAllseancesbydates(String da) {
        return seancecourrepo.findSeanceCoursByDates(da);
    }

}