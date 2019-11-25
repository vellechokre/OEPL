package com.oepl.importData.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oepl.document.OEPLMember;

/**
 * Project oepl-service
 *
 * @author homeshc
 * @version 1.0
 * @date 03-Jan-2019
 */
public interface OEPLMemberRepo extends JpaRepository<OEPLMember, Integer> {

    List<OEPLMember> findByAssigned(boolean assigned);

    List<OEPLMember> findByOeplTeamId(int id);
}
