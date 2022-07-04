package co.com.tita.platform.modules.dataprovider.jpa;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import co.com.tita.platform.crosscutting.domain.UserAccountTransactionDto;
import co.com.tita.platform.crosscutting.domain.UserEntityDto;
import co.com.tita.platform.crosscutting.enums.FilterPropertyValue;
import co.com.tita.platform.crosscutting.enums.MessageEnum;
import co.com.tita.platform.crosscutting.payload.CreateTransactionPayload;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.crosscutting.persistence.entity.model.Bank;
import co.com.tita.platform.crosscutting.persistence.entity.model.Loan;
import co.com.tita.platform.crosscutting.persistence.entity.model.User;
import co.com.tita.platform.crosscutting.persistence.entity.model.UserAccountTransaction;
import co.com.tita.platform.crosscutting.persistence.entity.repository.LoanRepository;
import co.com.tita.platform.crosscutting.persistence.entity.repository.UserAccountTransactionRepository;
import co.com.tita.platform.crosscutting.persistence.entity.repository.UserRepository;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.PaginationResponse;
import co.com.tita.platform.modules.dataprovider.TransactionDataProvider;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class TransactionJpaDataProvider implements TransactionDataProvider {

	@Autowired
	private UserAccountTransactionRepository accountTransactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ApiResponse<PaginationResponse<UserAccountTransactionDto>> getListTransaction(GetPaginablePayload payload) {
		
		ApiResponse<PaginationResponse<UserAccountTransactionDto>> response = new ApiResponse<>();

		try {
			Sort sort = payload.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
					? Sort.by(payload.getSortBy()).ascending()
					: Sort.by(payload.getSortBy()).descending();

			/// Stablish Pagination Users
			Pageable pagination = PageRequest.of(payload.getPageNumber(), payload.getItemPerPage(), sort);
			
			Page<UserAccountTransaction> bankPageable = filterTransaction(payload,pagination);

			List<UserAccountTransaction> transactionEntity = bankPageable.getContent();
			
			if (transactionEntity.isEmpty()) {
				response.setData(null);
				response.setMessage(MessageEnum.NO_EXISTE_REGISTRO.getMessage());
				response.setSuccess(MessageEnum.NO_EXISTE_REGISTRO.getStatus());
				response.setStatusCode(MessageEnum.NO_EXISTE_REGISTRO.getStatusCode());
				return response;
			}

			List<UserAccountTransactionDto> userResponseDto= new ArrayList<>();
					
			for(var item :transactionEntity ) {
				
				var user= userRepository.findById(item.getUserId());
				
				if(user.isPresent()) {
					var userDto=modelMapper.map(user.get(), UserEntityDto.class);
					var userAccountDto=modelMapper.map(item,UserAccountTransactionDto.class);
					userAccountDto.setUser(userDto);		
					userResponseDto.add(userAccountDto);
				}	
			}
			
			PaginationResponse<UserAccountTransactionDto> paginationResponse = new PaginationResponse<>();
			paginationResponse.setContent(userResponseDto);
			paginationResponse.setNumberPage(bankPageable.getNumber());
			paginationResponse.setPageSize(bankPageable.getSize());
			paginationResponse.setTotalPages(bankPageable.getTotalPages());
			paginationResponse.setTotalRecords(bankPageable.getNumberOfElements());
			paginationResponse.setLast(bankPageable.isLast());

			response.setData(paginationResponse);
			response.setMessage("");
			response.setSuccess(true);
			response.setStatusCode(HttpStatus.OK.value());
			return response;		
			
		}catch(Exception ex) {
			response.setData(null);
			response.setMessage(MessageEnum.ERROR_GENERAL.getMessage());
			response.setSuccess(MessageEnum.ERROR_GENERAL.getStatus());
			response.setStatusCode(MessageEnum.ERROR_GENERAL.getStatusCode());
			return response;
		}
	}
	
	private Page<UserAccountTransaction> filterTransaction(GetPaginablePayload payload, Pageable pagination) {
	
		if (payload.getFilterProperty() != null && !payload.getFilterProperty().isEmpty()
				&& payload.getFilterValue() != null && !payload.getFilterValue().isEmpty()) {

			//// Filtro por UserId
			if (payload.getFilterProperty().equalsIgnoreCase(FilterPropertyValue.TRANSACTIO_USER_ID.getValue())) {

				return accountTransactionRepository.findByUserId(payload.getFilterValue(), pagination);
			}

			/// Filtro por Identifcation
			if (payload.getFilterProperty().equalsIgnoreCase(FilterPropertyValue.TRANSACTIO_USER_ACCOUNT_TRANSACTION_ID.getValue())) {

				return accountTransactionRepository.findByUserAccountTransactionId(payload.getFilterValue(), pagination);
			}
		}
		return accountTransactionRepository.findAll(pagination);
		
	}


	@Override
	public ApiResponse<UserAccountTransactionDto> createTransaction(CreateTransactionPayload payload) {
		ApiResponse<UserAccountTransactionDto> response = new ApiResponse<>();

		try {

			var userExist = userRepository.findById(payload.getUserId());
			var lonaExist = loanRepository.findById(payload.getLoanId());

			// Find UserId verify if exist
			if (!userExist.isPresent()) {
				response.setData(null);
				response.setMessage(MessageEnum.NO_EXISTE_REGISTRO_USUARIO.getMessage());
				response.setStatusCode(MessageEnum.NO_EXISTE_REGISTRO_USUARIO.getStatusCode());
				response.setSuccess(MessageEnum.NO_EXISTE_REGISTRO_USUARIO.getStatus());
				return response;
			}

			// Find LoanId verify Exist
			if (!lonaExist.isPresent()) {
				response.setData(null);
				response.setMessage(MessageEnum.NO_EXISTE_REGISTRO_LOAN.getMessage());
				response.setStatusCode(MessageEnum.NO_EXISTE_REGISTRO_LOAN.getStatusCode());
				response.setSuccess(MessageEnum.NO_EXISTE_REGISTRO_LOAN.getStatus());
				return response;
			}

			return calculatePayment(payload, lonaExist.get(), userExist.get());

		} catch (Exception ex) {
			response.setData(null);
			response.setMessage(ex.getMessage());
			response.setStatusCode(MessageEnum.ERROR_GENERAL.getStatusCode());
			response.setSuccess(MessageEnum.ERROR_GENERAL.getStatus());
			return response;
		}
	}

	private ApiResponse<UserAccountTransactionDto> calculatePayment(CreateTransactionPayload payload, Loan loan,
			User user) {
		ApiResponse<UserAccountTransactionDto> response = new ApiResponse<>();

		try {
			
			// Se verifica si la deuda ha sido cancelada en su totalidad
			if (loan.getCuotasPagadas() == loan.getTotalCuotas()) {
				response.setData(null);
				response.setMessage(MessageEnum.DEUDA_CANCELADA.getMessage());
				response.setStatusCode(MessageEnum.DEUDA_CANCELADA.getStatusCode());
				response.setSuccess(MessageEnum.DEUDA_CANCELADA.getStatus());
				return response;
			}
			
			// Ver cuotas a pagar
			if ((payload.getCuotasApagar() + loan.getCuotasPagadas()) > loan.getTotalCuotas()) {
				response.setData(null);
				response.setMessage(MessageFormat.format(MessageEnum.CUOTAS_MAYOR_AL_REAL.getMessage(),
						(loan.getTotalCuotas() - loan.getCuotasPagadas())));
				response.setStatusCode(MessageEnum.CUOTAS_MAYOR_AL_REAL.getStatusCode());
				response.setSuccess(MessageEnum.CUOTAS_MAYOR_AL_REAL.getStatus());
				return response;
			}

			//Calcular valor e Cuota mínima
			if(payload.getIncome() < (int) loan.getSaldoTotalPrestamo()/loan.getTotalCuotas()) {
				response.setData(null);
				response.setMessage(MessageFormat.format(MessageEnum.VALOR_CUOTA_ERRONEO.getMessage(),(int)(loan.getSaldoTotalPrestamo()/loan.getTotalCuotas())));
				response.setStatusCode(MessageEnum.VALOR_CUOTA_ERRONEO.getStatusCode());
				response.setSuccess(MessageEnum.VALOR_CUOTA_ERRONEO.getStatus());
				return response;			
			}
			
			
			//Calcula el total a pagar a partir dél número de cuotas
			var valorCalculopagar=(payload.getCuotasApagar()*(int)(loan.getSaldoTotalPrestamo()/loan.getTotalCuotas()));
			
			//Calculo total monto X número de cuotas a pagar
			if(payload.getIncome()!=valorCalculopagar) {
				
				response.setData(null);
				response.setMessage(MessageFormat.format(MessageEnum.VALOR_CUOTA_POR_CANTIDAD_CUOTAS.getMessage(),valorCalculopagar));
				response.setStatusCode(MessageEnum.VALOR_CUOTA_POR_CANTIDAD_CUOTAS.getStatusCode());
				response.setSuccess(MessageEnum.VALOR_CUOTA_POR_CANTIDAD_CUOTAS.getStatus());
				return response;		
			}

			// Verifica el monto a pagar
			if ((payload.getIncome() + loan.getSaldoAbonado()) > loan.getSaldoTotalPrestamo()) {
				response.setData(null);
				response.setMessage(MessageFormat.format(MessageEnum.VALOR_MAYOR_AL_REAL.getMessage(),
						(loan.getSaldoTotalPrestamo() - loan.getSaldoAbonado())));
				response.setStatusCode(MessageEnum.VALOR_MAYOR_AL_REAL.getStatusCode());
				response.setSuccess(MessageEnum.VALOR_MAYOR_AL_REAL.getStatus());
				return response;
			}

			loan.setUpdatedAt(new Date());
			loan.setSaldoAbonado(loan.getSaldoAbonado() + (payload.getCuotasApagar()*loan.getSaldoTotalPrestamo()/loan.getTotalCuotas()));
			loan.setCuotasPagadas(loan.getCuotasPagadas() + payload.getCuotasApagar());

			loanRepository.save(loan);

			// Saved Transaction
			var entitySave = new UserAccountTransaction();
			entitySave.setUserAccountTransactionId(UUID.randomUUID().toString().toUpperCase());
			entitySave.setUserId(user.getUserId());
			entitySave.setBankId(loan.getBankId());
			entitySave.setLoanId(loan.getLoanId());
			entitySave.setIncome((payload.getCuotasApagar()*loan.getSaldoTotalPrestamo()/loan.getTotalCuotas()));
			entitySave.setFee(payload.getCuotasApagar());
			entitySave.setCreatedAt(new Date());
			entitySave.setUpdatedAt(new Date());

			accountTransactionRepository.save(entitySave);

			var responseDto = modelMapper.map(entitySave, UserAccountTransactionDto.class);
			var userDto = modelMapper.map(user, UserEntityDto.class);

			responseDto.setUser(userDto);

			response.setData(responseDto);
			response.setMessage(MessageFormat.format(MessageEnum.TRANSACCION_EXITOSA.getMessage(),
					entitySave.getUserAccountTransactionId()));
			response.setStatusCode(MessageEnum.TRANSACCION_EXITOSA.getStatusCode());
			response.setSuccess(MessageEnum.TRANSACCION_EXITOSA.getStatus());
			return response;

		} catch (Exception ex) {
			response.setData(null);
			response.setMessage(ex.getMessage());
			response.setStatusCode(MessageEnum.ERROR_GENERAL.getStatusCode());
			response.setSuccess(MessageEnum.ERROR_GENERAL.getStatus());
			return response;
		}
	}

}
