package com.oepl.document;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author homeshc
 * @version 1.0
 * @date 03-Jan-2019
 */
@Entity
@Table(name = "oeplMembers")
public class OEPLMember {

    @Id
    private int id;

    private int employeeId;

    private String name;

    private String project;

    private String skill;

    private String team;

    private int price;

    private String imageUrl;

    @JsonIgnore
    private boolean assigned;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "oeplTeam_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OEPLTeam oeplTeam;

    private double number;

    public int getId() {

        return id;
    }

    public void setId(final int id) {

        this.id = id;
    }

    public int getEmployeeId() {

        return employeeId;
    }

    public void setEmployeeId(final int employeeId) {

        this.employeeId = employeeId;
    }

    public String getName() {

        return name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public String getProject() {

        return project;
    }

    public void setProject(final String project) {

        this.project = project;
    }

    public String getSkill() {

        return skill;
    }

    public void setSkill(final String skill) {

        this.skill = skill;
    }

    public String getTeam() {

        return team;
    }

    public void setTeam(final String team) {

        this.team = team;
    }

    public int getPrice() {

        return price;
    }

    public void setPrice(final int price) {

        this.price = price;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {

        this.imageUrl = imageUrl;
    }

    public boolean isAssigned() {

        return assigned;
    }

    public void setAssigned(boolean assigned) {

        this.assigned = assigned;
    }

    /**
     * @return the oeplTeam
     */
    public OEPLTeam getOeplTeam() {

        return oeplTeam;
    }

    /**
     * @param oeplTeam the oeplTeam to set
     */
    public void setOeplTeam(OEPLTeam oeplTeam) {

        this.oeplTeam = oeplTeam;
    }

    /**
     * @return the number
     */
    public double getNumber() {

        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(double number) {

        this.number = number;
    }
}
