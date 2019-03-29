package com.ejtrio.springdatarest.infrastructure.repositories;

import com.ejtrio.springdatarest.infrastructure.models.Item;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
}
