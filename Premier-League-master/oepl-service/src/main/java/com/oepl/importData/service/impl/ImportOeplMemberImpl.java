package com.oepl.importData.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.oepl.core.Response;
import com.oepl.document.OEPLMember;
import com.oepl.document.OEPLTeam;
import com.oepl.importData.repo.OEPLMemberRepo;
import com.oepl.importData.repo.OEPLTeamRepo;
import com.oepl.importData.service.ImportOeplMember;

/**
 * Project oepl-service
 *
 * @author homeshc
 * @version 1.0
 * @date 03-Jan-2019
 */
@Service
public class ImportOeplMemberImpl implements ImportOeplMember {

    @Autowired
    private OEPLMemberRepo oeplRepo;

    @Autowired
    private OEPLTeamRepo teamRepo;

    private Workbook getSheet(final MultipartFile multiPartFile,
                              final String fileName) throws IOException {

        final File file = new File(fileName);
        Files.write(multiPartFile.getBytes(), file);
        Workbook workbook = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }
        }
        return workbook;
    }

    private Map<String, Integer> getColumnNamesAndCellNo(final Row row) {

        // to set key for the cell name
        final Map<String, Integer> cellNames = new HashMap<>();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            cellNames.put(row.getCell(i).toString(), i);
        }
        return cellNames;
    }

    @Override
    public void importData(final MultipartFile file, final String fileName) {

        /*
         * Create Workbook instance for xlsx/xls file input stream
         */
        try (Workbook workbook = getSheet(file, fileName)) {
            /*
             * Get the number of sheets in the xlsx file
             */
            final Iterator<Row> rowIterator = workbook.getSheetAt(0).iterator();
            final List<OEPLMember> oepls = new ArrayList<>();
            // to set key for the cell name
            Map<String, Integer> cellNames = new HashMap<>();
            while (rowIterator.hasNext()) {
                final OEPLMember oepl = new OEPLMember();
                final Row row = rowIterator.next();
                // skip first row because these are column names
                if (row.getRowNum() == 0) {
                    cellNames = getColumnNamesAndCellNo(row);
                    continue;
                }
                buildOEPL(oepl, row, cellNames);
                // changing the group object into BasicDBObject and adding that into the array of
                // documents
                oepls.add(oepl);
            }
            // save oepl
            oeplRepo.saveAll(oepls);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param oepl
     * @param row
     * @param cellNames
     */
    private void buildOEPL(final OEPLMember oepl, final Row row,
                           final Map<String, Integer> cellNames) {

        oepl.setId((int) (!ObjectUtils.isEmpty(row.getCell(cellNames.get("Sl.No."))) ? row
                .getCell(cellNames.get("Sl.No.")).getNumericCellValue() : 0));
        oepl.setEmployeeId((int) (!ObjectUtils.isEmpty(row.getCell(cellNames.get("ID"))) ? row
                .getCell(cellNames.get("ID")).getNumericCellValue() : 0));
        oepl.setName(!ObjectUtils.isEmpty(row.getCell(cellNames.get("Name"))) ? row
                .getCell(cellNames.get("Name")).toString() : null);
        oepl.setProject(!ObjectUtils.isEmpty(row.getCell(cellNames.get("Project"))) ? row
                .getCell(cellNames.get("Project")).toString() : null);
        oepl.setSkill(!ObjectUtils.isEmpty(row.getCell(cellNames.get("Skill"))) ? row
                .getCell(cellNames.get("Skill")).toString() : null);
        oepl.setTeam(!ObjectUtils.isEmpty(row.getCell(cellNames.get("Team"))) ? row
                .getCell(cellNames.get("Team")).toString() : null);
        oepl.setTeam(!ObjectUtils.isEmpty(row.getCell(cellNames.get("Team"))) ? row
                .getCell(cellNames.get("Team")).toString() : null);
        oepl.setPrice(0);
        oepl.setImageUrl(!ObjectUtils.isEmpty(row.getCell(cellNames.get("URL"))) ? row
                .getCell(cellNames.get("URL")).toString() : null);
        oepl.setNumber(!ObjectUtils.isEmpty(row.getCell(cellNames.get("Contact"))) ? row
                .getCell(cellNames.get("Contact")).getNumericCellValue() : 9999999999d);
    }

    @Override
    public List<OEPLMember> findAll(Boolean assigned) {

        if (Objects.isNull(assigned))
            return oeplRepo.findAll();
        else
            return oeplRepo.findByAssigned(assigned);
    }

    @Override
    public ResponseEntity<Response> addMemeber(final int teamId, final OEPLMember member) {

        member.setAssigned(true);
        final OEPLTeam team = teamRepo.findById(teamId);
        member.setOeplTeam(team);
        oeplRepo.save(member);
        team.setAmount(team.getAmount() - member.getPrice());
        teamRepo.save(team);
        // this.teamRepo.addMember(teamId, member);
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), "Suces"), HttpStatus.OK);
    }

    @Override
    public void removeMember(final int teamId, final int memberId) {

        // teamRepo.removeMember(teamId, memberId);
    }

    @Override
    public void markAsCaptian(final int teamId, final OEPLMember member) {

        // teamRepo.markAsCaptian(teamId, member);
    }

    @Override
    public OEPLTeam myTeam(final int teamId) {

        return teamRepo.findById(teamId);
    }

    @Override
    public HttpServletResponse exportTeams(final HttpServletResponse response) throws Throwable {

        final List<OEPLTeam> teams = getAllTeam();
        try (Workbook createdWorkbook = new XSSFWorkbook();) {
            final Sheet team1 = createdWorkbook.createSheet(teams.get(0).getTeamName());
            writeInSheet(team1, teams.get(0).getMembers(), teams.get(0));
            final Sheet team2 = createdWorkbook.createSheet(teams.get(1).getTeamName());
            writeInSheet(team2, teams.get(1).getMembers(), teams.get(1));
            final Sheet team3 = createdWorkbook.createSheet(teams.get(2).getTeamName());
            writeInSheet(team3, teams.get(2).getMembers(), teams.get(2));
            final Sheet team4 = createdWorkbook.createSheet(teams.get(3).getTeamName());
            writeInSheet(team4, teams.get(3).getMembers(), teams.get(3));
            final Sheet team5 = createdWorkbook.createSheet(teams.get(4).getTeamName());
            writeInSheet(team5, teams.get(4).getMembers(), teams.get(4));
            return generateResponseForExcelSheet(response, createdWorkbook);
        } catch (final IOException e) {
            throw e.getCause();
        }
    }

    public void writeInSheet(final Sheet sheet, final List<OEPLMember> members,
                             final OEPLTeam team) {

        Row createdRow;
        createdRow = sheet.createRow(0);
        final Cell teamName = createdRow.createCell(1);
        teamName.setCellValue("Team :- " + team.getTeamName());
        // if (!ObjectUtils.isEmpty(team.getCaptian())) {
        // createdRow = sheet.createRow(1);
        // final Cell captain = createdRow.createCell(1);
        // captain.setCellValue("Captain :- " + team.getCaptian().getName());
        // }
        // set headers
        if (!CollectionUtils.isEmpty(team.getMembers())) {
            createdRow = sheet.createRow(4);
            final CellStyle style = sheet.getWorkbook().createCellStyle();
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            createdRow.setRowStyle(style);
            final Cell idH = createdRow.createCell(0);
            idH.setCellValue("ID");
            final Cell employeeIdH = createdRow.createCell(1);
            employeeIdH.setCellValue("Employee ID");
            final Cell nameH = createdRow.createCell(2);
            nameH.setCellValue("Name");
            final Cell projectH = createdRow.createCell(3);
            projectH.setCellValue("Project");
            final Cell skillH = createdRow.createCell(4);
            skillH.setCellValue("Skill");
            final Cell teamH = createdRow.createCell(5);
            teamH.setCellValue("Contact");
            final Cell priceH = createdRow.createCell(6);
            priceH.setCellValue("Price");
            final Cell imageUrlH = createdRow.createCell(7);
            imageUrlH.setCellValue("Image Url");
            // set team members
            for (final OEPLMember member : members) {
                createdRow = sheet.createRow(sheet.getLastRowNum() + 1);
                final Cell id = createdRow.createCell(0);
                id.setCellValue(member.getId());
                final Cell employeeId = createdRow.createCell(1);
                employeeId.setCellValue(member.getEmployeeId());
                final Cell name = createdRow.createCell(2);
                name.setCellValue(member.getName());
                final Cell project = createdRow.createCell(3);
                project.setCellValue(member.getProject());
                final Cell skill = createdRow.createCell(4);
                skill.setCellValue(member.getSkill());
                final Cell number = createdRow.createCell(5);
                number.setCellValue(
                        StringUtils.isEmpty(member.getNumber()) ? 999999999d : member.getNumber());
                final Cell price = createdRow.createCell(6);
                price.setCellValue(StringUtils.isEmpty(member.getPrice()) ? 0 : member.getPrice());
                final Cell imageUrl = createdRow.createCell(7);
                imageUrl.setCellValue(
                        StringUtils.isEmpty(member.getImageUrl()) ? "NA" : member.getImageUrl());
            }
        }
    }

    public HttpServletResponse generateResponseForExcelSheet(final HttpServletResponse response,
                                                             final Workbook createdWorkbook) throws Throwable {

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            createdWorkbook.write(bos);
            response.addHeader("Content-disposition", "attachment;filename=" + "opel" + ".xlsx");
            // Copy the stream to the response's output stream.
            IOUtils.copy((new ByteArrayInputStream(bos.toByteArray())), response.getOutputStream());
            response.flushBuffer();
            return response;
        } catch (final IOException e) {
            throw e.getCause();
        }
    }

    @Override
    public List<OEPLTeam> getAllTeam() {

        List<OEPLTeam> teams = teamRepo.findAll();
        teams.forEach(team -> {
            List<OEPLMember> members = oeplRepo.findByOeplTeamId(team.getId());
            team.setMembers(
                    (!Objects.isNull(members) && !members.isEmpty()) ? members : new ArrayList<>());
            if (team.getCaptionId() > 0) {
                team.setCaptian(oeplRepo.findById(team.getId()).get());
            } else {
                team.setCaptian(findAll(null).get(0));
            }
        });
        return teams;
    }
}
