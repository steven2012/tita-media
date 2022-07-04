package co.com.tita.platform.modules.dataprovider;

import co.com.tita.platform.crosscutting.domain.UserAccountTransactionDto;
import co.com.tita.platform.crosscutting.payload.CreateTransactionPayload;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.PaginationResponse;

public interface TransactionDataProvider {

	public ApiResponse<PaginationResponse<UserAccountTransactionDto>> getListTransaction(GetPaginablePayload payload);

	public ApiResponse<UserAccountTransactionDto> createTransaction(CreateTransactionPayload payload);

}
