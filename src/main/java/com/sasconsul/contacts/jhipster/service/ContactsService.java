package com.sasconsul.contacts.jhipster.service;

import com.sasconsul.contacts.jhipster.domain.Contacts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Contacts.
 */
public interface ContactsService {

    /**
     * Save a contacts.
     * 
     * @param contacts the entity to save
     * @return the persisted entity
     */
    Contacts save(Contacts contacts);

    /**
     *  Get all the contacts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Contacts> findAll(Pageable pageable);

    /**
     *  Get the "id" contacts.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Contacts findOne(Long id);

    /**
     *  Delete the "id" contacts.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
