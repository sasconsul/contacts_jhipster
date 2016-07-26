package com.sasconsul.contacts.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sasconsul.contacts.jhipster.domain.Contacts;
import com.sasconsul.contacts.jhipster.service.ContactsService;
import com.sasconsul.contacts.jhipster.web.rest.util.HeaderUtil;
import com.sasconsul.contacts.jhipster.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Contacts.
 */
@RestController
@RequestMapping("/api")
public class ContactsResource {

    private final Logger log = LoggerFactory.getLogger(ContactsResource.class);
        
    @Inject
    private ContactsService contactsService;
    
    /**
     * POST  /contacts : Create a new contacts.
     *
     * @param contacts the contacts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contacts, or with status 400 (Bad Request) if the contacts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/contacts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contacts> createContacts(@RequestBody Contacts contacts) throws URISyntaxException {
        log.debug("REST request to save Contacts : {}", contacts);
        if (contacts.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contacts", "idexists", "A new contacts cannot already have an ID")).body(null);
        }
        Contacts result = contactsService.save(contacts);
        return ResponseEntity.created(new URI("/api/contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contacts", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts : Updates an existing contacts.
     *
     * @param contacts the contacts to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contacts,
     * or with status 400 (Bad Request) if the contacts is not valid,
     * or with status 500 (Internal Server Error) if the contacts couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/contacts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contacts> updateContacts(@RequestBody Contacts contacts) throws URISyntaxException {
        log.debug("REST request to update Contacts : {}", contacts);
        if (contacts.getId() == null) {
            return createContacts(contacts);
        }
        Contacts result = contactsService.save(contacts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contacts", contacts.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacts : get all the contacts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/contacts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Contacts>> getAllContacts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Contacts");
        Page<Contacts> page = contactsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contacts/:id : get the "id" contacts.
     *
     * @param id the id of the contacts to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contacts, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/contacts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contacts> getContacts(@PathVariable Long id) {
        log.debug("REST request to get Contacts : {}", id);
        Contacts contacts = contactsService.findOne(id);
        return Optional.ofNullable(contacts)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contacts/:id : delete the "id" contacts.
     *
     * @param id the id of the contacts to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/contacts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContacts(@PathVariable Long id) {
        log.debug("REST request to delete Contacts : {}", id);
        contactsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contacts", id.toString())).build();
    }

}
