package org.springframework.samples.petclinic.rest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Receipt;
import org.springframework.samples.petclinic.owner.ReceiptRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/receipt")
public class ReceiptRestController {

    private final ReceiptRepository receiptRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public ReceiptRestController(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @GetMapping
    public List<Receipt> findAll() {
        return receiptRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receipt> findById(@PathVariable Integer id) {
        Optional<Receipt> receiptOptional = receiptRepository.findById(id);
        return ResponseEntity.of(receiptOptional);
    }

    @PostMapping
    public Receipt save(@RequestBody @Valid Receipt entity) {
        return receiptRepository.save(entity);
    }

    // 1) does not work
    // Receipt receipt = receiptRepository.save(entity);
    // receipt = receiptRepository.findById(receipt.getId()).orElseThrow();

    // 2) does not work
    // Receipt receipt = receiptRepository.saveAndFlush(entity);
    // receipt = receiptRepository.findById(receipt.getId()).orElseThrow();

    // 3) works
    // @Transactional
    // entityManager.refresh(receipt);
    // see also https://stackoverflow.com/questions/45491551/refresh-and-fetch-an-entity-after-save-jpa-spring-data-hibernate

}
