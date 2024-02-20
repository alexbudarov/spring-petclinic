package org.springframework.samples.petclinic.owner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.amplicode.rautils.patch.ObjectPatcher;
import io.amplicode.rautils.patch.PatchValidationException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/owner")
public class OwnerRestController {

	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;
	private final ObjectMapper objectMapper;
	private final Validator validator;

	public OwnerRestController(OwnerRepository ownerRepository,
                               OwnerMapper ownerMapper, ObjectMapper objectMapper,
							   Validator validator) {
		this.ownerRepository = ownerRepository;
		this.ownerMapper = ownerMapper;
        this.objectMapper = objectMapper;
		this.validator = validator;
	}

	@GetMapping("/{id}")
	public ResponseEntity<OwnerDto> findById(@PathVariable Integer id) {
		Optional<Owner> ownerOptional = Optional.ofNullable(ownerRepository.findById(id));
		return ResponseEntity.of(ownerOptional.map(ownerMapper::toDto));
	}

	@GetMapping(path="/by-ids"/*, params = "id"*/)
	public List<OwnerDto> ownerListById(@RequestParam List<Integer> ids) {
		var dtoList = ownerRepository.findAllById(ids)
			.stream().map(ownerMapper::toDto)
			.toList();
		return dtoList;
	}

	@GetMapping/*(params="!id")*/
	public Page<OwnerDto> ownerList(@ModelAttribute OwnerFilter filter, Pageable pageable) {
		Specification<Owner> specification = filter.toSpecification();
		Page<Owner> page = ownerRepository.findAll(specification, pageable);
		return page.map(ownerMapper::toDto);
	}

	@PostMapping
	public ResponseEntity<OwnerDto> create(@RequestBody @Valid OwnerDto ownerDto) {
		if (ownerDto.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		Owner owner = ownerMapper.toEntity(ownerDto);
		ownerRepository.save(owner);
		owner = ownerRepository.findById(owner.getId());

		return ResponseEntity.ok(ownerMapper.toDto(owner));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<OwnerDto> update(@PathVariable Integer id, @RequestBody JsonNode ownerDtoPatch) throws IOException, BindException {
		Owner owner = ownerRepository.findById(id);
		if (owner == null) {
			return ResponseEntity.notFound().build();
		}

		OwnerDto ownerDto = ownerMapper.toDto(owner);

		// can be used only for mutable DTOs
		ownerDto = objectMapper.readerForUpdating(ownerDto).readValue(ownerDtoPatch);
		validate(ownerDto);

		if (ownerDto.getId() != null && !ownerDto.getId().equals(id)) {
			return ResponseEntity.badRequest().build();
		}

		ownerMapper.update(ownerDto, owner);
		ownerRepository.save(owner);
		owner = ownerRepository.findById(owner.getId());

		return ResponseEntity.ok(ownerMapper.toDto(owner));
	}

	@DeleteMapping
	public List<Integer> delete(@RequestParam List<Integer> ids) {
		List<Owner> existingEntities = ownerRepository.findAllById(ids);

		ownerRepository.deleteAllById(ids);

		return existingEntities.stream().map(e -> e.getId()).toList();
	}

	// library-independent validation method
	public void validate(Object target) throws BindException {
		DataBinder dataBinder = new DataBinder(target);
		dataBinder.setValidator(validator);
		dataBinder.validate();
		BindingResult bindResult = dataBinder.getBindingResult();
		if (bindResult.hasErrors()) {
			throw new BindException(bindResult);
		}
	}
}
