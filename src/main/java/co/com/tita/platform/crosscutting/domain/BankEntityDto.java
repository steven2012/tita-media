package co.com.tita.platform.crosscutting.domain;

import java.util.Date;
import java.util.List;

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
@Data
public class BankEntityDto {

	private String bankId;	

	private String name;
		
	private Date createdAt;
	
	private Date updatedAt;	
}
