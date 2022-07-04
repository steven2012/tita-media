package co.com.tita.platform.modules.dataprovider;

import co.com.tita.platform.crosscutting.domain.UserEntityDto;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.PaginationResponse;

public interface UserBankDataProvider {

	public ApiResponse<PaginationResponse<UserEntityDto>> getListuser(GetPaginablePayload payload);

}
