package com.sasconsul.contacts.jhipster.repository;

import com.sasconsul.contacts.jhipster.domain.Contacts;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contacts entity.
 */
public interface ContactsRepository extends JpaRepository<Contacts,Long> {

}
