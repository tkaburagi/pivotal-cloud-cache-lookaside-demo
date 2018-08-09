package io.pivotal.pccdemo.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.pivotal.pccdemo.domain.Customer;

@Repository("CustomerJpaRepository")
public interface CustomerJpaRepository extends JpaRepository<Customer, String> {

	Customer findByEmail(final String email);

}
