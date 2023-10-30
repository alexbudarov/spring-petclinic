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
	public ResponseEntity<List<OwnerDto>> ownerList(RaFilter filter,
													RaRangeSort range) {
		Object idFilterParam = filter.parameters.get("id");
		if (idFilterParam instanceof Object[]) {
			Page<Owner> entities = ownerRepository.findByIdIn((Object[]) idFilterParam, range.pageable);
			return raProtocolUtil.convertToResponseEntity(entities, ownerMapper::toDto, "owner");
		}

		String lastName = (String) filter.parameters.get("lastName");
		if (lastName == null) {
			lastName = "";
		}
		Page<Owner> page = ownerRepository.findByLastName(lastName, range.pageable);
		var response = raProtocolUtil.convertToResponseEntity(page, ownerMapper::toDto, "owner");
		return response;
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
		owner.setId(id);
		ownerRepository.save(owner);

		OwnerDto updatedDto = ownerMapper.toDto(ownerRepository.findById(id));
		return ResponseEntity.ok(updatedDto);
	}
}
