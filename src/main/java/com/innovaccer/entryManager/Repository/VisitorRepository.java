package com.innovaccer.entryManager.Repository;

import com.innovaccer.entryManager.Domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,Long> {

    @Query("Select v from Visitor v where emailId=?1")
    Visitor getVisitorByEmailId(String emailId);
}
