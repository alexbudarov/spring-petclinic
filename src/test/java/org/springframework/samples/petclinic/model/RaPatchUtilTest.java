package org.springframework.samples.petclinic.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.rest.rasupport.RaPatchUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class RaPatchUtilTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RaPatchUtil raPatchUtil;

	@Autowired
	private RaProtocolUtil raProtocolUtil;

    @Test
    void checkRecord() {
        String json = """
            {"id": 1, "telephone": 155, "city": null}
        """;

		RecordDto dto = new RecordDto(1, "109", "Samara", 7927714);

		dto = raPatchUtil.patch(dto, json);

        System.out.println(dto);

		assertEquals(155, dto.telephone()); // changed
		assertNull(dto.city()); // reset to null
		assertEquals("109", dto.address()); // not touched
    }

	@Test
	void checkMixedDto() {
		String json = """
            {"id": 1, "telephone": 155, "city": null, "name": "Alex"}
        """;

		MixedDto dto = new MixedDto(1, "109", 79277);
		dto.setCity("Samara");

		dto = raPatchUtil.patch(dto, json);

		System.out.println(dto);

		assertEquals(155, dto.getTelephone()); // changed via constructor
		assertNull(dto.getCity()); // reset to null
		assertEquals("109", dto.getAddress()); // not touched
		assertEquals("Alex", dto.getName()); // changed via setter
	}

	@Test
	void checkMutableDto() {
		String json = """
            {"id": 1, "telephone": 155, "city": null}
        """;

		MutableDto dto = new MutableDto();
		dto.setId(1);
		dto.setAddress("109");
		dto.setTelephone(79277);
		dto.setCity("Samara");

		dto = raPatchUtil.patch(dto, json);

		System.out.println(dto);

		assertEquals(155, dto.getTelephone()); // changed
		assertNull(dto.getCity()); // reset to null
		assertEquals("109", dto.getAddress()); // not touched
	}

	public record RecordDto(Integer id, @NotBlank String address,
						   @NotBlank String city, Integer telephone) {
	}

	/**
	 * DTO for {@link Owner}
	 */
	public static final class MixedDto {
		private final Integer id;
		private final String address;
		private String name;
		private String city;
		private final Integer telephone;

		public MixedDto(Integer id, String address, Integer telephone) {
			this.id = id;
			this.address = address;
			this.telephone = telephone;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public Integer getId() {
			return id;
		}

		public String getAddress() {
			return address;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCity() {
			return city;
		}

		public Integer getTelephone() {
			return telephone;
		}

		@Override
		public String toString() {
			return "MyOwnerDto{" +
				"id=" + id +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", name='" + name + '\'' +
				", telephone='" + telephone + '\'' +
				'}';
		}
	}

	/**
	 * DTO for {@link Owner}
	 */
	public static final class MutableDto {
		private Integer id;
		private String address;
		private String city;
		private Integer telephone;

		public void setCity(String city) {
			this.city = city;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setTelephone(Integer telephone) {
			this.telephone = telephone;
		}

		public Integer getId() {
			return id;
		}

		public String getAddress() {
			return address;
		}

		public String getCity() {
			return city;
		}

		public Integer getTelephone() {
			return telephone;
		}

		@Override
		public String toString() {
			return "MyOwnerDto{" +
				"id=" + id +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", telephone='" + telephone + '\'' +
				'}';
		}
	}
}
