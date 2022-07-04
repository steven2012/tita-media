package co.com.tita.platform.modules.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import co.com.tita.platform.crosscutting.domain.UserEntityDto;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.EbusinessConstant;
import co.com.tita.platform.modules.common.PaginationResponse;
import co.com.tita.platform.modules.usecase.ProcessUserBank;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = EbusinessConstant.USER_API, produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserBankController {

	@Autowired
	private ProcessUserBank processUserBank;

	@ApiOperation(value = "Obtain all user ", notes = "It allows obtaining the all users and yours bills")
	@GetMapping()
	public ResponseEntity<ApiResponse<PaginationResponse<UserEntityDto>>> getUsers(@Valid GetPaginablePayload payload) {
		return new ResponseEntity<>(processUserBank.getListuser(payload),HttpStatus.OK);
	}
}
