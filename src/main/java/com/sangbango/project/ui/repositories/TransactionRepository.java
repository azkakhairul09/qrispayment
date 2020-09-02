package com.sangbango.project.ui.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangbango.project.ui.entitymodel.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
	TransactionEntity findByTransactionId(String transactionId);
	List<TransactionEntity> findAllByTransactionId(String transactionId);
	List<TransactionEntity> findAll();
}
