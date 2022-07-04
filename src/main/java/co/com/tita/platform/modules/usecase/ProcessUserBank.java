package co.com.tita.platform.modules.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.tita.platform.crosscutting.domain.UserEntityDto;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.PaginationResponse;
import co.com.tita.platform.modules.dataprovider.UserBankDataProvider;

@Service
public class ProcessUserBank {

	@Autowired
	private UserBankDataProvider userBankDataProvider;
	
	public ApiResponse<PaginationResponse<UserEntityDto>> getListuser(GetPaginablePayload payload) {
		return userBankDataProvider.getListuser(payload);
	}

}
