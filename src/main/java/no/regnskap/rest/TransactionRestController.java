package no.regnskap.rest;

import no.regnskap.domain.Transaction;
import no.regnskap.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping(value = "/api/")
@RestController
public class TransactionRestController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/transaction/", method = RequestMethod.POST)
    public ResponseEntity<Void> save(@RequestBody Transaction transaction, UriComponentsBuilder ucBuilder) {
        if (transactionService.findById(transaction.getTransactionId()) != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        transactionService.save(transaction);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("/transaction/{id}").buildAndExpand(transaction.getTransactionId()).toUri());
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> findById(@PathVariable("id") long id) {
        Transaction transaction = transactionService.findById(id);

        if (transaction == null) {
            return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }

    @RequestMapping(value = "/transaction/", method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactionList = transactionService.getAllTransactions();

        if (transactionList.isEmpty()) {
            return new ResponseEntity<List<Transaction>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Transaction>>(transactionList, HttpStatus.OK);
    }
}
