package com.innovaccer.entryManager.Repository;

import com.innovaccer.entryManager.Domain.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {

    @Query("select h from Host h where h.emailId=?1")
    Host getHostByEmailId(String hostEmailId);

    @Query("select count(h) from Host h where h.emailId=?1")
    Integer checkActiveHostByEmail(String hostEmailId);

    @Transactional
    @Modifying
    @Query(value = "delete  from Host h where h.hostId=?1")
    void deleteHost(Long hostId);

}
