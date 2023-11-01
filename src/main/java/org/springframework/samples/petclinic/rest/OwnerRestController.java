package org.springframework.samples.petclinic.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerDto;
import org.springframework.samples.petclinic.owner.OwnerMapper;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSort;
import org.springframework.samples.petclinic.rest.rasupport.ReactAdminFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/owner")
public class OwnerRestController {

	private final OwnerRepository ownerRepository;
	private final RaProtocolUtil raProtocolUtil;
	private final OwnerMapper ownerMapper;

	public OwnerRestController(OwnerRepository ownerRepository,
							   RaProtocolUtil raProtocolUtil,
							   OwnerMapper ownerMapper) {
		this.ownerRepository = ownerRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.ownerMapper = ownerMapper;
	}

	@GetMapping("/{id}")
	public ResponseEntity<OwnerDto> findById(@PathVariable Integer id) {
		Owner ownerOrNull = ownerRepository.findById(id);
		return ResponseEntity.ofNullable(ownerMapper.toDto(ownerOrNull));
	}

	@GetMapping
	public ResponseEntity<List<OwnerDto>> ownerList(@ReactAdminFilter OwnerListFilter filter,
													RaRangeSort range) {
		Page<Owner> page;
		if (filter.id() != null) {
			page = ownerRepository.findByIdIn(filter.id(), range.pageable);
		} else if (filter.lastName() != null) {
			page = ownerRepository.findByLastName(filter.lastName(), range.pageable);
		} else {
			page = ownerRepository.findAll(range.pageable);
		}
		return raProtocolUtil.convertToResponseEntity(page, ownerMapper::toDto, "owner");
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public OwnerDto create(@RequestBody @Valid OwnerDto ownerDto) {
		Owner owner = ownerMapper.toEntity(ownerDto);
		owner.setId(null);
		ownerRepository.save(owner);
		return ownerMapper.toDto(owner);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OwnerDto> update(@PathVariable Integer id, @RequestBody @Valid OwnerDto ownerDto) {
		if (ownerDto.id() != null && !ownerDto.id().equals(id)) {
			return ResponseEntity.badRequest().build();
		}
		Owner owner = ownerRepository.findById(id);
		ownerMapper.update(ownerDto, owner);
		ownerRepository.save(owner);

		OwnerDto updatedDto = ownerMapper.toDto(ownerRepository.findById(id));
		return ResponseEntity.ok(updatedDto);
	}

	public record OwnerListFilter(Integer[] id, String lastName) {
	}
}
