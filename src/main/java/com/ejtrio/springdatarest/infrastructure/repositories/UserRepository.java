package com.ejtrio.springdatarest.infrastructure.repositories;

import com.ejtrio.springdatarest.infrastructure.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    List<User> findByLastName(@Param("lastName") String lastName);
}

