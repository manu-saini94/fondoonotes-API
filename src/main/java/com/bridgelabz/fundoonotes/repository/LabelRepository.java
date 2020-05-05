package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Labels;


@Repository
@Transactional
public interface LabelRepository extends JpaRepository<Labels,Long> {

	@Query("from Labels where labelname=?1 and users_details_id=?2")
	Labels getLabelByName(String labelname,Long id);
	
	@Query("delete from Labels where id=?1 and users_details_id=?2")
	@Modifying 
	public Integer deleteLabelInUser(Long id1, Long id2);

	@Query("update Labels set labelname=?1 where id=?2 and users_details_id=?3")
	@Modifying
	public Integer renameLabel(String labelname, Long id1, Long id2);
	
	@Query(value=" select * from notes where id in (select notes_id from notes_labels where labels_id=:labelID)",nativeQuery = true)
	public Object[] displayNotes(Long labelID);

	@Query("from Labels where id=?1 and users_details_id=?2")
	Labels getLabelByUser(Long id1, Long id2);

	@Query(value="select * from labels where users_details_id=?1",nativeQuery=true)
	List<Labels> getLabelNamesByUser(Long id);
	
	
	
	

	
}
