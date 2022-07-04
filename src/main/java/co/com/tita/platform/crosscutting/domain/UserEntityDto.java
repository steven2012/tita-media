package co.com.tita.platform.crosscutting.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import co.com.tita.platform.crosscutting.persistence.entity.model.Loan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserEntityDto {

	private String userId;
	
	private String userName;

	private String identification;

	private Long age;
	
	private String email;
	
	private List<LoanEntityDto> loan;

	private Date createdAt;
	
	private Date updatedAt;	
}
