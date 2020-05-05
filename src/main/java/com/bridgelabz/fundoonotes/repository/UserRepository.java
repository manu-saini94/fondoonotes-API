package com.bridgelabz.fundoonotes.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.model.Users;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

	@Query(value = "insert into users(mobile_number,first_name,last_name,email_id,password) values(:mobileNumber,:firstName,:lastName,:emailId,:password)", nativeQuery = true)
	@Modifying
	public Integer SaveUser(String mobileNumber, String firstName, String lastName, String emailId, String password);

	@Query("from Users where email_id=?1")
	Users findByEmail(String email);

	@Query(value = "update users set password=:password where email_id=:uemail", nativeQuery = true)
	@Modifying
	public void changepassword(String password, String uemail);

	@Query(value = "update users set is_email_verified=true where email_id=:uemail", nativeQuery = true)
	@Modifying
	public Integer setVerifiedEmail(String uemail);

}
