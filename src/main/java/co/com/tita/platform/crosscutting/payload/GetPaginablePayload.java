package co.com.tita.platform.crosscutting.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetPaginablePayload {

    @Min(value=0 ,message="el campo pageNumber debe m�nimo iniciar en 0")
    @Max(value=100000 ,message="el campo pageNumber no debe ser mayor que  10000")
    private int pageNumber;
   
    @Min(value=1 ,message="el campo itemPerPage debe iniciar m�nimo en 1")
    @Max(value=100000 ,message="el campo itemPerPage no debe ser mayor que 10000")
	private int itemPerPage;
 
    @Value("asc")
    @NotNull(message="el campo sortDir no debe ser nulo")
    @NotEmpty(message="el campo sortDir no debe  estar vacio")
    private String sortDir;

    @NotNull(message="el campo sortBy no debe ser nulo ")
    @NotEmpty(message="el campo sortBy no debe estar vacio")
    private String sortBy;
    
    private String filterProperty;

    private String filterValue;
}
