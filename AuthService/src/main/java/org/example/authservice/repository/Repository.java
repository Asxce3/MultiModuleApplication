package org.example.authservice.repository;

import org.example.authservice.models.Candidate;
import org.example.authservice.repository.DAO.CandidateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Repository {
    @Autowired
    CandidateImpl candidateImpl;

    public ResponseEntity<?> checkUser(Candidate candidate) {
        try {
            Optional<Candidate> user = candidateImpl.checkUser(candidate);

            if (user.isPresent()) return ResponseEntity.ok().body(user.get());
            else {
                return ResponseEntity.notFound().build();
            }


        }   catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
