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
public class LoanEntityDto {

	private String loanId;
	
	private String producto;	
	
	private int cuotasPagadas;
	
	private int totalCuotas;

	private double  saldoAbonado;

	private double  saldoTotalPrestamo;
	
	private Date createdAt;
	
	private Date updatedAt;	
	
	private String userId;
	
	private BankEntityDto bank;
}
