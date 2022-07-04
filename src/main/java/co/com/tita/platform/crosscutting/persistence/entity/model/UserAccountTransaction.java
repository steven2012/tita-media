package co.com.tita.platform.crosscutting.persistence.entity.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "UserAccountTransaction")
public class UserAccountTransaction implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "UserAccountTransactionId")
	private String userAccountTransactionId;
	
	@Basic(optional = false)
	@Column(name = "User_Id")
	private String userId;	
	
	@Basic(optional = false)
	@Column(name = "Bank_Id")
	private String bankId;	
	
	@Basic(optional = false)
	@Column(name = "Loan_Id")
	private String loanId;
	
	@Basic(optional = false)
	@Column(name = "Fee")
	private int fee;
	
	@Basic(optional = false)
	@Column(name = "Income")
	private double income;
	
	@Column(name = "CreatedAt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "UpdatedAt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;	
	
}
