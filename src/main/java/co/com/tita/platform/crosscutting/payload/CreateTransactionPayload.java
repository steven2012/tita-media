package co.com.tita.platform.crosscutting.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTransactionPayload {
	
	@NotNull(message="El campo userId no debe ser nulo")
	@NotEmpty(message="El campo userId no debe ser vacio")
	private String userId;	
		
	@NotNull(message="El campo loanId no debe ser nulo")
	@NotEmpty(message="El campo loanId no debe ser vacio")
	private String loanId;
	
	@Max(value=60 ,message="el campo cuotasApagar no debe exceder los 60")
	@Min(value=1 ,message="el campo cuotasApagar debe iniciar en 1")
	private int  cuotasApagar;
	
    @Min(value=1000 ,message="el campo income debe m�nimo iniciar en 1000")
	private double income;
	
	
}
