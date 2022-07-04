package co.com.tita.platform.modules.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.tita.platform.crosscutting.domain.BankEntityDto;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.EbusinessConstant;
import co.com.tita.platform.modules.common.PaginationResponse;
import co.com.tita.platform.modules.usecase.ProcessBank;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = EbusinessConstant.BANK_API, produces = { MediaType.APPLICATION_JSON_VALUE })
public class BankController {

	
	@Autowired
	private ProcessBank processBank;

	@ApiOperation(value = "Obtain all banks ", notes = "It allows obtaining all banks registered")
	@GetMapping()
	public ResponseEntity<ApiResponse<PaginationResponse<BankEntityDto>>> getUsers(GetPaginablePayload payload) {
		return new ResponseEntity<>(processBank.getListBank(payload),HttpStatus.OK);
	}
	
}
