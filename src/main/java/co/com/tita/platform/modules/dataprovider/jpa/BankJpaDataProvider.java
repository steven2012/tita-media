package co.com.tita.platform.modules.dataprovider.jpa;

import java.util.ArrayList;
import java.util.List;

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
import co.com.tita.platform.crosscutting.enums.FilterPropertyValue;
import co.com.tita.platform.crosscutting.enums.MessageEnum;
import co.com.tita.platform.crosscutting.payload.GetPaginablePayload;
import co.com.tita.platform.crosscutting.persistence.entity.model.Bank;
import co.com.tita.platform.crosscutting.persistence.entity.repository.BankRepository;
import co.com.tita.platform.modules.common.ApiResponse;
import co.com.tita.platform.modules.common.PaginationResponse;
import co.com.tita.platform.modules.dataprovider.BankDataProvider;

@Component
public class BankJpaDataProvider implements BankDataProvider{

	@Autowired
	private BankRepository bankRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ApiResponse<PaginationResponse<BankEntityDto>> getListBank(GetPaginablePayload payload) {
		ApiResponse<PaginationResponse<BankEntityDto>> response = new ApiResponse<>();

		try {

			Sort sort = payload.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
					? Sort.by(payload.getSortBy()).ascending()
					: Sort.by(payload.getSortBy()).descending();

			/// Stablish Pagination Users
			Pageable pagination = PageRequest.of(payload.getPageNumber(), payload.getItemPerPage(), sort);
			
			Page<Bank> bankPageable = filterBank(payload,pagination);

			List<Bank> bankEntity = bankPageable.getContent();

			if (bankEntity.isEmpty()) {
				response.setData(null);
				response.setMessage(MessageEnum.NO_EXISTE_REGISTRO.getMessage());
				response.setSuccess(MessageEnum.NO_EXISTE_REGISTRO.getStatus());
				response.setStatusCode(MessageEnum.NO_EXISTE_REGISTRO.getStatusCode());
				return response;
			}

			// Maper UserEntity to userResponseDto
			List<BankEntityDto> userResponseDto = modelMapper.map(bankEntity, new TypeToken<List<BankEntityDto>>() {
			}.getType());

			PaginationResponse<BankEntityDto> paginationResponse = new PaginationResponse<>();
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
			
		} catch (Exception ex) {
			response.setData(null);
			response.setMessage(MessageEnum.ERROR_GENERAL.getMessage());
			response.setSuccess(MessageEnum.ERROR_GENERAL.getStatus());
			response.setStatusCode(MessageEnum.ERROR_GENERAL.getStatusCode());
			return response;		
			}
	}
	
	
	private Page<Bank> filterBank(GetPaginablePayload payload, Pageable pagination) {

		if (payload.getFilterProperty() != null && !payload.getFilterProperty().isEmpty()
				&& payload.getFilterValue() != null && !payload.getFilterValue().isEmpty()) {

			//// Filtro por UserId
			if (payload.getFilterProperty().equalsIgnoreCase(FilterPropertyValue.BANK_ID.getValue())) {

				return bankRepository.findByBankId(payload.getFilterValue(), pagination);
			}

			/// Filtro por Identifcation
			if (payload.getFilterProperty().equalsIgnoreCase(FilterPropertyValue.BANK_NAME.getValue())) {

				return bankRepository.findByName(payload.getFilterValue(), pagination);
			}
		}
		return bankRepository.findAll(pagination);
	}

}
