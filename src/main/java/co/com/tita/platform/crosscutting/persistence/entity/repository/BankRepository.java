package co.com.tita.platform.crosscutting.persistence.entity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import co.com.tita.platform.crosscutting.persistence.entity.model.Bank;

@Repository
public interface BankRepository extends PagingAndSortingRepository<Bank, String>{

	Page<Bank> findByBankId(String bankId, Pageable pageable);
	
	Page<Bank> findByName(String name, Pageable pageable);

	
}
