package com.example.accounts.repository;

import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// this is not have implementation so interface naming convention is not followed eg:- IAccount
@Repository
public interface AccountsRepository extends JpaRepository<Accounts,String>
{
       Optional<Accounts> findByCustomerId(Long customerId);

       @Transactional
       @Modifying
    void deleteByCustomerId(Long customerId);
       /* when we try to change data in database through custom method then we need to apply
       two annotation that is @Transactional and @Modifying. @Modifying this tells the sprring data JPA that it will modify the data. And
       @Transactional tells create a query for it within the transaction. if query cause any error while transaction going on , spring data JPA will roll back the entire changes to its previous.
        */

}
