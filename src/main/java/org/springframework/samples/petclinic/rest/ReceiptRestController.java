package org.springframework.samples.petclinic.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Receipt;
import org.springframework.samples.petclinic.owner.ReceiptRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/receipt")
public class ReceiptRestController {

    private final ReceiptRepository receiptRepository;

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
}
