package com.oepl.importData.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oepl.document.OEPLTeam;

/**
 * Project oepl-service
 *
 * @author homeshc
 * @version 1.0
 * @date 03-Jan-2019
 */
public interface OEPLTeamRepo extends JpaRepository<OEPLTeam, Integer>/*, OEPLTeamRepoCustom */ {

    OEPLTeam findById(int id);
}
