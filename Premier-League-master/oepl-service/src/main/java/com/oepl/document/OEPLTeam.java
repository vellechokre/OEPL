package com.oepl.document;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Project oepl-service
 *
 * @author nishantb
 * @version 1.0
 * @date 03-Jan-2019
 */
@Entity
@Table(name = "OEPLTeams")
public class OEPLTeam {

    @Id
    private int id;

    private String teamName;

    @JsonIgnore
    private int captionId = 0;

    @Transient
    private OEPLMember captian;

    @Transient
    private List<OEPLMember> members = new ArrayList<>();

    private int amount = 9000;

    private String imageUrl;

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getTeamName() {

        return teamName;
    }

    /**
     * @return the captionId
     */
    public int getCaptionId() {

        return captionId;
    }

    /**
     * @param captionId the captionId to set
     */
    public void setCaptionId(int captionId) {

        this.captionId = captionId;
    }

    public void setTeamName(final String teamName) {

        this.teamName = teamName;
    }

    public OEPLMember getCaptian() {

        return captian;
    }

    public void setCaptian(final OEPLMember captian) {

        this.captian = captian;
    }

    public List<OEPLMember> getMembers() {

        return members;
    }

    public void setMembers(final List<OEPLMember> members) {

        this.members = members;
    }

    public int getAmount() {

        return amount;
    }

    public void setAmount(final int amount) {

        this.amount = amount;
    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {

        return imageUrl;
    }

    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {

        this.imageUrl = imageUrl;
    }
}
