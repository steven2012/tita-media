package co.com.tita.platform.modules.common;

public class EbusinessConstant {

	private EbusinessConstant() {
	}
	
     ////JPA DB
	public static final String ENTITY_MANAGER_FACTORY="entityManagerFactory";
	public static final String TRANSACTION_MANAGER="transactionManager";
	public static final String DATASOURCE_USER= "spring.datasource";
	public static final String JPA_DATASOURCE_NAME = "db-user-bank";

	public static final String BASE_PACKAGES="co.com.tita.platform.crosscutting.persistence.entity.repository";
	public static final String ENTITY_PACKAGES="co.com.tita.platform.crosscutting.persistence.entity.model";
	public static final String UNIT_PERSITENCE="master";
	
	//Controller
	public static final String USER_API = "/user";
	public static final String BANK_API = "/bank";
	public static final String BANK_TRANSACTION = "/transaction";



	
}
