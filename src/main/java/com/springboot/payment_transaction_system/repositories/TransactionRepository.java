package com.springboot.payment_transaction_system.repositories;

import com.springboot.payment_transaction_system.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {


}
