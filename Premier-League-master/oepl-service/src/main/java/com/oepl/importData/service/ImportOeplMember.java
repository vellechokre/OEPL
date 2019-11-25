package com.oepl.importData.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.oepl.core.Response;
import com.oepl.document.OEPLMember;
import com.oepl.document.OEPLTeam;

/**
 * Project oepl-service
 *
 * @author homeshc
 * @version 1.0
 * @date 03-Jan-2019
 */
public interface ImportOeplMember {

  /**
   * @param file
   * @param fileName
   */
  void importData(MultipartFile file, String fileName);

  /**
   * @param assigned
   * @return
   */
  List<OEPLMember> findAll(Boolean assigned);

  /**
   * @param teamId
   * @param member
   * @return
   */
  ResponseEntity<Response> addMemeber(int teamId, OEPLMember member);

  /**
   * @param teamId
   * @param memberId
   */
  void removeMember(int teamId, int memberId);

  /**
   * @param teamId
   * @param member
   */
  void markAsCaptian(int teamId, OEPLMember member);

  /**
   * @param teamId
   * @return
   */
  OEPLTeam myTeam(int teamId);

  /**
   * @param response
   * @return
   * @throws Throwable
   */
  HttpServletResponse exportTeams(HttpServletResponse response) throws Throwable;

  /**
   * @return
   */
  List<OEPLTeam> getAllTeam();
}
