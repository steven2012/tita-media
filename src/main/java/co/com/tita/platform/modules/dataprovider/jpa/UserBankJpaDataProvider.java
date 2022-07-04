package co.com.tita.platform.modules.dataprovider.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import co.com.tita.platform.crosscutting.domain.BankEntityDto;
import co.com.tita.platform.crosscutting.domain.LoanEntityDto;
import co.com.tita.platform.crosscutting.domain.UserEntityDto;
import co.com.tita.platform.crosscutting.enums.FilterPropertyValue;
import co.com.tita.platform.crosscutting.enums.MessageEnum;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.crosscutting.persistence.entity.model.Bank;
import co.com.tita.platform.crosscutting.persistence.entity.model.Loan;
import co.com.tita.platform.crosscutting.persistence.entity.model.User;
import co.com.tita.platform.crosscutting.persistence.entity.repository.BankRepository;
import co.com.tita.platform.crosscutting.persistence.entity.repository.LoanRepository;
import co.com.tita.platform.crosscutting.persistence.entity.repository.UserRepository;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.PaginationResponse;
import co.com.tita.platform.modules.dataprovider.UserBankDataProvider;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class UserBankJpaDataProvider implements UserBankDataProvider{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ApiResponse<PaginationResponse<UserEntityDto>> getListuser(GetPaginablePayload payload) {

		ApiResponse<PaginationResponse<UserEntityDto>> response = new ApiResponse<>();

		try {
			Sort sort = payload.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
					? Sort.by(payload.getSortBy()).ascending()
					: Sort.by(payload.getSortBy()).descending();

			/// Stablish Pagination Users
			Pageable pagination = PageRequest.of(payload.getPageNumber(), payload.getItemPerPage(), sort);

			///Filter Pagination
			Page<User> userPagination = filterModelUser(payload,pagination);

			/// Obtain all users
			List<User> userEntity = userPagination!=null?userPagination.getContent():new ArrayList<>();

			if (userEntity.isEmpty()) {
				response.setData(null);
				response.setMessage(MessageEnum.NO_EXISTE_REGISTRO.getMessage());
				response.setSuccess(MessageEnum.NO_EXISTE_REGISTRO.getStatus());
				response.setStatusCode(MessageEnum.NO_EXISTE_REGISTRO.getStatusCode());
				return response;
			}
			
			// Maper UserEntity to userResponseDto
			List<UserEntityDto> userResponseDto = modelMapper.map(userEntity, new TypeToken<List<UserEntityDto>>() {
			}.getType());
			
			for(var item : userResponseDto) {
				
				//Aqui se verifica filtro por lOAN
				List<Loan> listfilterLoan=filterModelLoan(payload);
				
				if(listfilterLoan==null) {
					response.setData(null);
					response.setMessage(MessageEnum.NO_EXISTE_REGISTRO.getMessage());
					response.setSuccess(MessageEnum.NO_EXISTE_REGISTRO.getStatus());
					response.setStatusCode(MessageEnum.NO_EXISTE_REGISTRO.getStatusCode());
					return response;			
				}
				
				
				if(!listfilterLoan.isEmpty()) {
					var listLoanBankDto=buildBankDtoUser(listfilterLoan);
					item.setLoan(listLoanBankDto);
					userResponseDto.removeIf(x->!x.getUserId().equalsIgnoreCase(item.getUserId()));
					break;
				}
				
				Optional<List<Loan>> listLoan=loanRepository.findByUserId(item.getUserId());
				if(listLoan.isPresent()) {
					var listLoanBankDto=buildBankDtoUser(listLoan.get());
					item.setLoan(listLoanBankDto);
				}	
			}
		

			PaginationResponse<UserEntityDto> paginationResponse=new PaginationResponse<>();
			paginationResponse.setContent(userResponseDto);
			paginationResponse.setNumberPage(userPagination.getNumber());
			paginationResponse.setPageSize(userPagination.getSize());
			paginationResponse.setTotalPages(userPagination.getTotalPages());
			paginationResponse.setTotalRecords(userPagination.getNumberOfElements());
			paginationResponse.setLast(userPagination.isLast());	
			
			response.setData(paginationResponse);
			response.setMessage("");
			response.setSuccess(true);
			response.setStatusCode(HttpStatus.OK.value());
			return response;

		} catch (Exception ex) {
			response.setData(null);
			response.setMessage(MessageEnum.ERROR_GENERAL.getMessage());
			response.setSuccess(MessageEnum.ERROR_GENERAL.getStatus());
			response.setStatusCode(MessageEnum.ERROR_GENERAL.getStatusCode());
			return response;
			
		}
	}
	
	
	
	private Page<User> filterModelUser(GetPaginablePayload payload,Pageable pagination){
				
		try {
			if(payload.getFilterProperty()!=null && !payload.getFilterProperty().isEmpty() &&
					payload.getFilterValue()!=null && !payload.getFilterValue().isEmpty()) {
				
				////Filtro por UserId
				if(payload.getFilterProperty().equalsIgnoreCase(FilterPropertyValue.USER_ID.getValue())) {
					
					return userRepository.findByUserId(payload.getFilterValue(), pagination);
				}
			
				///Filtro por Identifcation
				if(payload.getFilterProperty().equalsIgnoreCase(FilterPropertyValue.IDENTIFICATION.getValue())) {
					
					return userRepository.findByIdentification(payload.getFilterValue(), pagination);
				}
			}
				return userRepository.findAll(pagination);
			
		}
		catch(Exception ex) {
			log.info(ex.getMessage());
			return null;

		}
	}
	
	private List<Loan> filterModelLoan(GetPaginablePayload payload) {
		List<Loan> listLoan = null;

		try {

			if (payload.getFilterProperty() != null && !payload.getFilterProperty().isEmpty()
					&& payload.getFilterValue() != null && !payload.getFilterValue().isEmpty()
					&& payload.getFilterProperty().equalsIgnoreCase(FilterPropertyValue.LOAN_ID.getValue())) {

				//// Filtro por UserId
				var entity = loanRepository.findById(payload.getFilterValue());

				if (!entity.isPresent()) {
					return listLoan;
				}

				listLoan= new ArrayList<>();
				listLoan.add(entity.get());
				return listLoan;
			}
			
			return new ArrayList<>();

		} catch (Exception ex) {
			log.info(ex.getMessage());
			return listLoan;

		}
	}
	
	private List<LoanEntityDto> buildBankDtoUser(List<Loan> lisLoan) {
		
		List<LoanEntityDto> listItem= new ArrayList<>();
		
		for(var item :lisLoan) {
			
			Optional<Bank> bank=bankRepository.findById(item.getBankId());
			
			if(bank.isPresent()) {
				
				var bankDto= modelMapper.map(bank.get(),BankEntityDto.class);
				var loanDtoEntity=modelMapper.map(item,LoanEntityDto.class);
				
				loanDtoEntity.setBank(bankDto);
				listItem.add(loanDtoEntity);
			}
		}
		return listItem;
	}
	


}
