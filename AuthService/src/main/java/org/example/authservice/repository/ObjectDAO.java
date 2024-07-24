package org.example.authservice.repository;

import org.example.authservice.models.Candidate;

import java.util.Optional;

public interface ObjectDAO {

    Optional<Candidate> checkUser(Candidate candidate);
}
