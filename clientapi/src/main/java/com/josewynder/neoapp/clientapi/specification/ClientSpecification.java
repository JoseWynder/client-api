package com.josewynder.neoapp.clientapi.specification;

import com.josewynder.neoapp.clientapi.model.Client;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ClientSpecification {

    public static Specification<Client> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Client> hasCpf(String cpf) {
        return (root, query, cb) ->
                cpf == null ? null : cb.equal(root.get("cpf"), cpf);
    }

    public static Specification<Client> hasBirthDate(LocalDate birthDate) {
        return (root, query, cb) ->
                birthDate == null ? null : cb.equal(root.get("birthDate"), birthDate);
    }

    public static Specification<Client> hasEmail(String email) {
        return (root, query, cb) ->
                email == null ? null : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Client> hasPhone(String phone) {
        return (root, query, cb) ->
                phone == null ? null : cb.like(cb.lower(root.get("phone")), "%" + phone.toLowerCase() + "%");
    }
}
