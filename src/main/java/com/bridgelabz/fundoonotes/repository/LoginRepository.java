package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.model.Users;

@Repository
public interface LoginRepository extends JpaRepository<Users,String> {

	@Query("from Users where firstname=?1")
	Users findByUsername(String firstname);

	
}
