package com.innovaccer.entryManager.Repository;

import com.innovaccer.entryManager.Domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query(value = "select count(m.vhmId) from Meeting m where m.visitorId=?1 and m.checkoutTime=null or m.checkoutTime=''")
    Integer getActiveCheckedInCnt(Long visitorId);

    @Query(value = "select m from Meeting m where m.visitorId=?1")
    Meeting getAllDetails(Long visitorId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Meeting m set m.checkoutTime=?2 where m.visitorId=?1")
    void updateCheckoutTime(Long visitorId, String checkoutTime);

    @Transactional
    @Modifying
    @Query(value = "delete from Meeting m where m.visitorId=?1")
    void deleteMeeting(Long visitorId);

}
