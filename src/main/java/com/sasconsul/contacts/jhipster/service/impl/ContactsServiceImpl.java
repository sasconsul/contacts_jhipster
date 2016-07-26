package com.sasconsul.contacts.jhipster.service.impl;

import com.sasconsul.contacts.jhipster.service.ContactsService;
import com.sasconsul.contacts.jhipster.domain.Contacts;
import com.sasconsul.contacts.jhipster.repository.ContactsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Contacts.
 */
@Service
@Transactional
@Component
public class ContactsServiceImpl implements ContactsService {

    private final Logger log = LoggerFactory.getLogger(ContactsServiceImpl.class);

    @Inject
    private ContactsRepository contactsRepository;

    /**
     * Save a contacts.
     *
     * @param contacts the entity to save
     * @return the persisted entity
     */
    public Contacts save(Contacts contacts) {
        log.debug("Request to save Contacts : {}", contacts);
        Contacts result = contactsRepository.save(contacts);
        return result;
    }

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Contacts> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        Page<Contacts> result = contactsRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one contacts by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Contacts findOne(Long id) {
        log.debug("Request to get Contacts : {}", id);
        Contacts contacts = contactsRepository.findOne(id);
        return contacts;
    }

    /**
     * Delete the  contacts by id.h
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Contacts : {}", id);
        contactsRepository.delete(id);
    }

    @Override
    // public Page<Contacts> findAllNotDeleted(Pageable pageable) {
    /**
     *  Get all un-deleted the contacts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Contacts> findAllNotDeleted(Pageable pageable) {
        log.debug("Request to get all un-deleted Contacts");
        Page<Contacts> result = contactsRepository.findAllNotDeleted(pageable);
        return result;
    }
}
