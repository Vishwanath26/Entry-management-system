package com.innovaccer.entryManager.Repository;

import com.innovaccer.entryManager.Domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    @Query("Select v from Visitor v where emailId=?1")
    Visitor getVisitorByEmailId(String emailId);

    @Transactional
    @Modifying
    @Query("delete from Visitor v where v.visitorId=?1")
    void deleteVisitor(Long visitorId);
}
