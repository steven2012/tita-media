package co.com.tita.platform.modules.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.tita.platform.crosscutting.domain.BankEntityDto;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.PaginationResponse;
import co.com.tita.platform.modules.dataprovider.BankDataProvider;

@Service
public class ProcessBank {

	@Autowired
	private BankDataProvider bankDataProvider;
	
	public ApiResponse<PaginationResponse<BankEntityDto>> getListBank(GetPaginablePayload payload) {
		return bankDataProvider.getListBank(payload);
	}
}
