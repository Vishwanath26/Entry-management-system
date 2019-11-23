package com.innovaccer.entryManager.Repository;

import com.innovaccer.entryManager.Domain.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host,Long> {

}
