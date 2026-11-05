package dio.marketplace.registration.infrastructure.persistence.repository;

import dio.marketplace.registration.domain.entity.Projection.CustomerExcerpt;
import dio.marketplace.registration.domain.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;
@RepositoryRestResource(excerptProjection = CustomerExcerpt.class)
public interface CustomerEntityRepository extends PagingAndSortingRepository<Customer, UUID>, CrudRepository<Customer, UUID> {
    List<Customer> findByFirstNameStartingWithIgnoreCase(@Param("firstname") String firstName);

    void deleteById(UUID id);


}
