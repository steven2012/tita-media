package co.com.tita.platform.modules.dataprovider;

import co.com.tita.platform.crosscutting.domain.BankEntityDto;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.PaginationResponse;

public interface BankDataProvider {
	
	public ApiResponse<PaginationResponse<BankEntityDto>> getListBank(GetPaginablePayload payload);

}
