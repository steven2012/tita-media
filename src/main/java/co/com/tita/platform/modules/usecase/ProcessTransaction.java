package co.com.tita.platform.modules.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.tita.platform.crosscutting.domain.UserAccountTransactionDto;
import co.com.tita.platform.crosscutting.payload.CreateTransactionPayload;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.PaginationResponse;
import co.com.tita.platform.modules.dataprovider.TransactionDataProvider;

@Service
public class ProcessTransaction {

	@Autowired
	private TransactionDataProvider transactionDataProvider;
	
	public ApiResponse<PaginationResponse<UserAccountTransactionDto>> getListTransaction(GetPaginablePayload payload) {
	
		return transactionDataProvider.getListTransaction(payload);
	}

	public ApiResponse<UserAccountTransactionDto> createTransaction(CreateTransactionPayload payload) {
		return transactionDataProvider.createTransaction(payload);
	}

}
