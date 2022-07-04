package co.com.tita.platform.crosscutting.persistence.entity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.com.tita.platform.crosscutting.persistence.entity.model.User;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
	
	Page<User> findByUserId(String userId, Pageable pageable);
	
	Page<User> findByIdentification(String identification, Pageable pageable);


}
