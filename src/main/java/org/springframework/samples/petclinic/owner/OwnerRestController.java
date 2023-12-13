package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeParam;
import org.springframework.samples.petclinic.rest.rasupport.RaSortParam;
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

	public OwnerRestController(OwnerRepository ownerRepository,
                               RaProtocolUtil raProtocolUtil,
                               OwnerMapper ownerMapper) {
		this.ownerRepository = ownerRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.ownerMapper = ownerMapper;
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

		Page<Owner> page = ownerRepository.findAll(pageable);
		return raProtocolUtil.convertToResponseEntity(page, ownerMapper::toDto);
	}

	public record OwnerListFilter(
		List<Integer> id
	) {
	}
}