package no.regnskap.service;

import no.regnskap.domain.Transaction;

import java.util.List;

public interface TransactionService {

    void save(Transaction transaction);

    Transaction findById(long id);

    List<Transaction> getAllTransactions();
}
