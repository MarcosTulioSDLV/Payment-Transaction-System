package com.springboot.payment_transaction_system.repositories;

import com.springboot.payment_transaction_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByDocument(String document);

    boolean existsByEmail(String email);


    //Optional<User> findByDocument(String document);

}
