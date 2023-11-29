package org.springframework.samples.petclinic.owner;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/owner")
public class OwnerRestController {

	private final OwnerRepository ownerRepository;
	private final RaProtocolUtil raProtocolUtil;
	private final OwnerMapper ownerMapper;
	private final SpecificationFilterConverter specificationFilterConverter;
	private final RaPatchUtil raPatchUtil;

	public OwnerRestController(OwnerRepository ownerRepository,
                               RaProtocolUtil raProtocolUtil,
                               OwnerMapper ownerMapper,
                               SpecificationFilterConverter specificationFilterConverter,
                               RaPatchUtil raPatchUtil) {
		this.ownerRepository = ownerRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.ownerMapper = ownerMapper;
		this.specificationFilterConverter = specificationFilterConverter;
		this.raPatchUtil = raPatchUtil;
	}

	@GetMapping("/{id}")
	public ResponseEntity<OwnerDto> findById(@PathVariable Integer id) {
		Owner ownerOrNull = ownerRepository.findById(id);
		return ResponseEntity.ofNullable(ownerMapper.toDto(ownerOrNull));
	}

	@GetMapping
	public ResponseEntity<List<OwnerDto>> ownerList(@RaFilter OwnerListFilter filter,
													@RaRangeParam @RaSortParam Pageable pageable) {

		if (filter.id() != null) { // getMany
			var dtoList = ownerRepository.findAllById(filter.id())
					.stream().map(ownerMapper::toDto)
					.toList();
			return ResponseEntity.ok(dtoList);
		}
		Specification<Owner> specification = specificationFilterConverter.convert(filter);
		Page<Owner> page = ownerRepository.findAll(specification, pageable);

		return raProtocolUtil.convertToResponseEntity(page, ownerMapper::toDto);
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
	public ResponseEntity<OwnerDto> update(@PathVariable Integer id, @RequestBody String ownerDtoPatch) {
		Owner owner = ownerRepository.findById(id);
		if (owner == null) {
			return ResponseEntity.notFound().build();
		}

		OwnerDto ownerDto = ownerMapper.toDto(owner);
		ownerDto = raPatchUtil.patchAndValidate(ownerDto, ownerDtoPatch);
		if (ownerDto.id() != null && !ownerDto.id().equals(id)) {
			return ResponseEntity.badRequest().build();
		}

		ownerMapper.update(ownerDto, owner);
		ownerRepository.save(owner);

		OwnerDto updatedDto = ownerMapper.toDto(ownerRepository.findById(id));
		return ResponseEntity.ok(updatedDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<OwnerDto> delete(@PathVariable Integer id) {
		Optional<OwnerDto> visitDto = Optional.ofNullable(ownerRepository.findById(id))
				.map(ownerMapper::toDto);

		if (visitDto.isPresent()) {
			ownerRepository.deleteById(id);
		}
		return ResponseEntity.of(visitDto);
	}

	public record OwnerListFilter(
		List<Integer> id,

		@SpecFilterCondition(operator = SpecFilterOperator.CONTAINS, ignoreCase = true)
		String lastName,

		@SpecFilterCondition(operator = SpecFilterOperator.STARTS_WITH)
		String telephone
	) {
	}

}

