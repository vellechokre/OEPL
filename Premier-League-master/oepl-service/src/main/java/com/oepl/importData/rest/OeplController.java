package com.oepl.importData.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oepl.core.Response;
import com.oepl.document.OEPLMember;
import com.oepl.document.OEPLTeam;
import com.oepl.importData.service.ImportOeplMember;

/**
 * Project oepl-service
 *
 * @author homeshc
 * @version 1.0
 * @date 03-Jan-2019
 */
@RestController
@RequestMapping("/members")
public class OeplController {

  @Autowired
  private ImportOeplMember oeplService;

  @PostMapping(value = "import", consumes = "multipart/form-data",
      produces = "application/vnd.ms-excel")
  public void importOepl(@RequestParam("fileName") final String fileName,
      @RequestParam("file") final MultipartFile file) {

    this.oeplService.importData(file, fileName);
  }

  @GetMapping(value = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<OEPLMember> findAll(@RequestParam(required = false) Boolean assigned) {

    return this.oeplService.findAll(assigned);
  }

  @PostMapping(value = "addMember", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response> addMember(@RequestParam("teamId") final int teamId,
      @RequestBody final OEPLMember member) {

    return this.oeplService.addMemeber(teamId, member);
  }

  @GetMapping(value = "removeMember", produces = MediaType.APPLICATION_JSON_VALUE)
  public void addMember(@RequestParam("teamId") final int teamId,
      @RequestParam("memberId") final int memberId) {

    this.oeplService.removeMember(teamId, memberId);
  }

  @GetMapping(value = "myTeam", produces = MediaType.APPLICATION_JSON_VALUE)
  public OEPLTeam myTeam(@RequestParam("teamId") final int teamId) {

    return this.oeplService.myTeam(teamId);
  }

  @PostMapping(value = "markAsCaptain", produces = MediaType.APPLICATION_JSON_VALUE)
  public void markAsCaptain(@RequestParam("teamId") final int teamId,
      @RequestBody final OEPLMember member) {

    this.oeplService.markAsCaptian(teamId, member);
  }

  @GetMapping(value = "export/team/xlsx", produces = "application/vnd.ms-excel")
  public void exportTeamXlsx(final HttpServletResponse response) throws Throwable {

    this.oeplService.exportTeams(response);
  }

  @GetMapping(value = "allTeam", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<OEPLTeam> allTeam() {

    return this.oeplService.getAllTeam();
  }
}
