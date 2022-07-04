package co.com.tita.platform.crosscutting.domain;

import java.util.Date;


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
public class UserAccountTransactionDto {
	
	private String userAccountTransactionId;
	
	private UserEntityDto user;	
	
	private String bankId;	
	
	private String loanId;
	
	private double income;
	
	private int  fee;

	private Date createdAt;
	
	private Date updatedAt;	
}
