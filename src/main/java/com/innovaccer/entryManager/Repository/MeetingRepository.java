package com.innovaccer.entryManager.Repository;

import com.innovaccer.entryManager.Domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {
}
