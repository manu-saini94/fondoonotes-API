package com.bridgelabz.fundoonotes.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.Users;

@Repository
@Transactional
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

	@Query("from Notes where id=?1")
	Notes getNotes(Long id);

	@Query("from Users where email_id=?1")
	Users findByEmail(String email);

	@Query(value = "select * from collaborator where collaborator=?1 and notes_id=?2", nativeQuery = true)
	Object getCollaborator(String collaborator, Long noteId);

	@Query(value = "delete from collaborator where collaborator=?1 and notes_id=?2", nativeQuery = true)
	@Modifying
	void deleteCollaboratorFromNote(String collaborator, Long noteId);

//	@Query("from Collaborator where collaborator=?1 and usersdetails_id=?2")
//	Labels getCollaboratorByName(String collaborator,int id);
//	
//	@Query("delete from Collaborator where id=?1 and usersdetails_id=?2")
//	@Modifying 
//	public Integer deleteCollaboratorInUser(int id1, int id2);
//
//	@Query("update Labels set labelname=?1 where id=?2 and usersdetails_id=?3")
//	@Modifying
//	public Integer renameCollaborator(String collaborator, int id1, int id2);
//	
//	@Query(value=" select * from notes where id in (select notes_id from notes_collaborator where collaborator_id=:collaboratorId)",nativeQuery = true)
//	public Object[] displayNotes(int collaboratorId);
//
//	@Query("from Collaborator where id=?1 and usersdetails=?2")
//	Labels getCollaboratorByUser(int id1, int id2);
//
//	@Query(value="select * from collaborator where usersdetails_id=?1",nativeQuery=true)
//	List<Collaborator> getCollaboratorNamesByUser(int id);

}
