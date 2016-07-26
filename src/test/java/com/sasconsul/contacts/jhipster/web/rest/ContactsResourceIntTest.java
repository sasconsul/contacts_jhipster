package com.sasconsul.contacts.jhipster.web.rest;

import com.sasconsul.contacts.jhipster.ContactsJhipsterApp;
import com.sasconsul.contacts.jhipster.domain.Contacts;
import com.sasconsul.contacts.jhipster.repository.ContactsRepository;
import com.sasconsul.contacts.jhipster.service.ContactsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ContactsResource REST controller.
 *
 * @see ContactsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactsJhipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class ContactsResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_PROFESSION = "AAAAA";
    private static final String UPDATED_PROFESSION = "BBBBB";

    private static final Boolean DEFAULT_EMPLOYED = false;
    private static final Boolean UPDATED_EMPLOYED = true;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_ON_STR = dateTimeFormatter.format(DEFAULT_CREATED_ON);

    private static final ZonedDateTime DEFAULT_MODIFIED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_MODIFIED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_MODIFIED_ON_STR = dateTimeFormatter.format(DEFAULT_MODIFIED_ON);

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;

    @Inject
    private ContactsRepository contactsRepository;

    @Inject
    private ContactsService contactsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContactsMockMvc;

    private Contacts contacts;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactsResource contactsResource = new ContactsResource();
        ReflectionTestUtils.setField(contactsResource, "contactsService", contactsService);
        this.restContactsMockMvc = MockMvcBuilders.standaloneSetup(contactsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contacts = new Contacts();
        contacts.setName(DEFAULT_NAME);
        contacts.setEmail(DEFAULT_EMAIL);
        contacts.setProfession(DEFAULT_PROFESSION);
        contacts.setEmployed(DEFAULT_EMPLOYED);
        contacts.setCreatedOn(DEFAULT_CREATED_ON);
        contacts.setModifiedOn(DEFAULT_MODIFIED_ON);
        contacts.setDeleted(DEFAULT_DELETED);
        contacts.setEmployeeId(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void createContacts() throws Exception {
        int databaseSizeBeforeCreate = contactsRepository.findAll().size();

        // Create the Contacts

        restContactsMockMvc.perform(post("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contacts)))
                .andExpect(status().isCreated());

        // Validate the Contacts in the database
        List<Contacts> contacts = contactsRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeCreate + 1);
        Contacts testContacts = contacts.get(contacts.size() - 1);
        assertThat(testContacts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContacts.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContacts.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testContacts.isEmployed()).isEqualTo(DEFAULT_EMPLOYED);
        assertThat(testContacts.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testContacts.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testContacts.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testContacts.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contacts
        restContactsMockMvc.perform(get("/api/contacts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
                .andExpect(jsonPath("$.[*].employed").value(hasItem(DEFAULT_EMPLOYED.booleanValue())))
                .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON_STR)))
                .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON_STR)))
                .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
                .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get the contacts
        restContactsMockMvc.perform(get("/api/contacts/{id}", contacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contacts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.employed").value(DEFAULT_EMPLOYED.booleanValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON_STR))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON_STR))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContacts() throws Exception {
        // Get the contacts
        restContactsMockMvc.perform(get("/api/contacts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContacts() throws Exception {
        // Initialize the database
        contactsService.save(contacts);

        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();

        // Update the contacts
        Contacts updatedContacts = new Contacts();
        updatedContacts.setId(contacts.getId());
        updatedContacts.setName(UPDATED_NAME);
        updatedContacts.setEmail(UPDATED_EMAIL);
        updatedContacts.setProfession(UPDATED_PROFESSION);
        updatedContacts.setEmployed(UPDATED_EMPLOYED);
        updatedContacts.setCreatedOn(UPDATED_CREATED_ON);
        updatedContacts.setModifiedOn(UPDATED_MODIFIED_ON);
        updatedContacts.setDeleted(UPDATED_DELETED);
        updatedContacts.setEmployeeId(UPDATED_EMPLOYEE_ID);

        restContactsMockMvc.perform(put("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedContacts)))
                .andExpect(status().isOk());

        // Validate the Contacts in the database
        List<Contacts> contacts = contactsRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeUpdate);
        Contacts testContacts = contacts.get(contacts.size() - 1);
        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContacts.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContacts.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testContacts.isEmployed()).isEqualTo(UPDATED_EMPLOYED);
        assertThat(testContacts.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testContacts.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testContacts.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testContacts.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void deleteContacts() throws Exception {
        // Initialize the database
        contactsService.save(contacts);

        int databaseSizeBeforeDelete = contactsRepository.findAll().size();

        // Get the contacts
        restContactsMockMvc.perform(delete("/api/contacts/{id}", contacts.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contacts> contacts = contactsRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
