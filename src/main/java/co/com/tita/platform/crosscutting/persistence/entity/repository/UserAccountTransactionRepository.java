package co.com.tita.platform.crosscutting.persistence.entity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.com.tita.platform.crosscutting.persistence.entity.model.UserAccountTransaction;

@Repository
public interface UserAccountTransactionRepository extends PagingAndSortingRepository<UserAccountTransaction, String> {

	Page<UserAccountTransaction> findByUserAccountTransactionId(String userAccountTransactionId,Pageable pageable);
	Page<UserAccountTransaction> findByUserId(String userId,Pageable pageable );
}
