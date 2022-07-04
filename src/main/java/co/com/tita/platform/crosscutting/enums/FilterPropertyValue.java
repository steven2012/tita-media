package co.com.tita.platform.crosscutting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FilterPropertyValue {

	///Property Model USER
	USER_ID("UserId"),
	USER_NAME("UserName"),
	IDENTIFICATION("Identification"),
	AGE("Age"),
	EMAIL("Email"),
	LOAN_ID("LoanId"),
	
	//PROPERTY BANK
	BANK_ID("BankId"),
	BANK_NAME("Name"),
	
	//Transaaction
	TRANSACTIO_USER_ID("UserId"),
	TRANSACTIO_USER_ACCOUNT_TRANSACTION_ID("userAccountTransactionId"),

	;
	private  String value;
}
