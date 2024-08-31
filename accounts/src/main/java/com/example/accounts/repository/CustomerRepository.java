package com.example.accounts.repository;

import com.example.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //gives bean representation
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Optional<Customer> findByMobileNo(String mobileNo);// because we dont no there is a customer with given  in db or not so we are using optionl


// mobileNO syntax should be same as defined in the entity

    // this is called derived name method because based on the method name spring data JPA is going to create sql queries.
}
