package co.com.tita.platform.modules.api.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.tita.platform.crosscutting.domain.UserAccountTransactionDto;
import co.com.tita.platform.crosscutting.payload.CreateTransactionPayload;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.EbusinessConstant;
import co.com.tita.platform.modules.common.PaginationResponse;
import co.com.tita.platform.modules.usecase.ProcessTransaction;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = EbusinessConstant.BANK_TRANSACTION, produces = { MediaType.APPLICATION_JSON_VALUE })
public class TransactionController {

	
	@Autowired
	private ProcessTransaction processTransaction;

	@ApiOperation(value = "Obtain all Transactions ", notes = "It allows obtaining all transactions")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ApiResponse<PaginationResponse<UserAccountTransactionDto>>> getUsers(@Valid GetPaginablePayload payload) {
		return new ResponseEntity<>(processTransaction.getListTransaction(payload),HttpStatus.OK);
	}
	
	@ApiOperation(value = "Allow create transaction ", notes = "Allow create transaction")
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ApiResponse<UserAccountTransactionDto>> createTransaction(@Valid @RequestBody CreateTransactionPayload payload) {
		return new ResponseEntity<>(processTransaction.createTransaction(payload),HttpStatus.OK);
	}
}
