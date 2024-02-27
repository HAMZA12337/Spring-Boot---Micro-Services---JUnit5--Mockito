package id.piko.customerservice.repositories;

import id.piko.customerservice.entities.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer,Long> {


     public Optional<Customer> findByEmail(String email);
     List<Customer> findByFirstNameContainsIgnoreCase(String keyword);



}
