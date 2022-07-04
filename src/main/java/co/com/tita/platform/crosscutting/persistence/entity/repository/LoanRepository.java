package co.com.tita.platform.crosscutting.persistence.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import co.com.tita.platform.crosscutting.persistence.entity.model.Loan;

@Repository
public interface LoanRepository extends PagingAndSortingRepository<Loan, String> {
	
	Optional<List<Loan>> findByUserId(String userId);
}
