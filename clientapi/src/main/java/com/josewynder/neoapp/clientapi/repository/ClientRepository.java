package com.josewynder.neoapp.clientapi.repository;

import com.josewynder.neoapp.clientapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

}
