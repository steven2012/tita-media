package co.com.tita.platform.crosscutting.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageEnum {

	NO_EXISTE_REGISTRO("No existe registro",HttpStatus.NOT_FOUND.value(),false),
	ERROR_GENERAL("Se presentó un error al procesar solictud",HttpStatus.BAD_REQUEST.value(),false),

	
	//USER
	NO_EXISTE_REGISTRO_USUARIO("No existe registro para el usuario con el id proporcionado",HttpStatus.NOT_FOUND.value(),false),
	NO_EXISTE_REGISTRO_LOAN("No existe registro para el Loan con el id proporcionado",HttpStatus.NOT_FOUND.value(),false),

	//Transaction
	CUOTAS_MAYOR_AL_REAL("No es posible pagar este n\u00famero de cuotas, el m\u00e1ximo de cuotas a pagar es {0}",HttpStatus.EXPECTATION_FAILED.value(),false),
	DEUDA_CANCELADA("La deuda ha sido cancelada en su totalidad",HttpStatus.OK.value(),false),
	VALOR_MAYOR_AL_REAL("no es posible cancelar el total indicado, el monto permitido a cancelar es {0}",HttpStatus.OK.value(),false),
	TRANSACCION_EXITOSA("Se realiz\u00f3  la transacci\u00f3n {0} de manera exitosa ",HttpStatus.OK.value(),true),
	VALOR_CUOTA_ERRONEO("El valor a cancelar no corresponde con el valor mínimo a pagar que es {0}}",HttpStatus.CONFLICT.value(),false),
	VALOR_CUOTA_POR_CANTIDAD_CUOTAS("El valor ingresado no corresponde de manera correcta con el núemro de cuotas a cancelar,"
			+ " El valor a cancelar para el n\u00famero de cuotas ingresada es  {0}}",HttpStatus.CONFLICT.value(),false)

	;

	private  String message;
	private  int  statusCode;
	private Boolean status;
}
