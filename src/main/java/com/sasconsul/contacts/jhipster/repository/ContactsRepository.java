package com.sasconsul.contacts.jhipster.repository;

import com.sasconsul.contacts.jhipster.domain.Contacts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Spring Data JPA repository for the Contacts entity.
 */
@Component
public interface ContactsRepository extends JpaRepository<Contacts,Long> {

    // FIXME need the null check because of a UI problem. [Need to visit the UI control for the value to be set.]
    @Query("select c from Contacts c where c.deleted <> TRUE")
    Page<Contacts> findAllNotDeleted(Pageable pageable);

    /**
     * Soft deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Query("UPDATE Contacts SET deleted = true WHERE id=:id")
    void delete( @Param("id") Long id);

    /**
     * Search recent 5 contacts added who are unemployed (Should have sorting and pagination support)"
     * @param pageable
     * @return
     */
//    @Query("select c from Contacts c where c.deleted <> TRUE and c.employed <> TRUE order by c.created_on ")
//    Page<Contacts> findByEmployedFalse(Pageable pageable);

}
