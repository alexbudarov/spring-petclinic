package org.springframework.samples.petclinic.owner;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/owner")
public class OwnerRestController {

	private final OwnerRepository ownerRepository;
	private final RaProtocolUtil raProtocolUtil;
	private final OwnerMapper ownerMapper;

	private final SpecificationFilterConverter specificationFilterConverter;

	public OwnerRestController(OwnerRepository ownerRepository,
                               RaProtocolUtil raProtocolUtil,
                               OwnerMapper ownerMapper,
							   SpecificationFilterConverter specificationFilterConverter) {
		this.ownerRepository = ownerRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.ownerMapper = ownerMapper;
		this.specificationFilterConverter = specificationFilterConverter;
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

		// handle custom searchString
		if (filter.searchString() != null) {
			Page<Owner> page = ownerRepository.findByFirstOrLastName(filter.searchString(), pageable);
			return raProtocolUtil.convertToResponseEntity(page, ownerMapper::toDto);
		}

		if (filter.telephone() != null) {
			Page<Owner> page = ownerRepository.findByTelephoneStartsWith(filter.telephone(), pageable);
			return raProtocolUtil.convertToResponseEntity(page, ownerMapper::toDto);
		}

		Page<Owner> page = ownerRepository.findAll(pageable);
		return raProtocolUtil.convertToResponseEntity(page, ownerMapper::toDto);
	}

	public record OwnerListFilter(
		List<Integer> id,

		@JsonProperty("q")
		String searchString,

		String telephone
	) {
	}
}