package com.innovaccer.entryManager.Repository;

import com.innovaccer.entryManager.Domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {

  @Query(value ="select count(m.vhmId) from Meeting m where m.visitorId=?1")
    Integer  getActiveCheckedInCnt(Long visitorId);
}
