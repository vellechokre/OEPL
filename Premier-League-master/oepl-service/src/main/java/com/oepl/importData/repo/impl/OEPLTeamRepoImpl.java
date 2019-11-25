// package com.oepl.importData.repo.impl;
//
// import javax.persistence.EntityManager;
// import javax.persistence.PersistenceContext;
// import javax.persistence.Query;
//
// import org.hibernate.Criteria;
// import org.hibernate.sql.Update;
//
// import com.oepl.document.OEPLMember;
// import com.oepl.document.OEPLTeam;
// import com.oepl.importData.repo.OEPLTeamRepoCustom;
//
/// **
// * Project oepl-service
// *
// * @author homeshc
// * @version 1.0
// * @date 03-Jan-2019
// */
// public class OEPLTeamRepoImpl implements OEPLTeamRepoCustom {
//
// @PersistenceContext
// EntityManager entityManager;
//
// /*
// * (non-Javadoc)
// *
// * @see com.oepl.member.repo.OEPLTeamRepoCustom#addMember(java.lang.String,
// * com.oepl.document.OEPLMember)
// */
// @Override
// public void addMember(final int teamId, final OEPLMember member) {
// Query query = entityManager.createNativeQuery("INSERT INTO VALUES", Employee.class);
// query.setParameter(1, firstName + "%");
// return query.getResultList();
//
//
// final Query query = new BaseQueryI();
// query.addCriteria(Criteria.where("_id").is(teamId));
// final Update update = new Update();
// update.push("members", member);
// mongoOperation.updateFirst(query, update, OEPLTeam.class);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see com.oepl.member.repo.OEPLTeamRepoCustom#removeMember(java.lang.String, java.lang.String)
// */
// @Override
// public void removeMember(final int teamId, final int memberId) {
//
// final Query query = new Query();
// query.addCriteria(Criteria.where("_id").is(teamId));
// final Update update = new Update();
// update.pull("members", new BasicDBObject("_id", new BasicDBObject("$eq", memberId)));
// mongoOperation.updateFirst(query, update, OEPLTeam.class);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see com.oepl.member.repo.OEPLTeamRepoCustom#markAsCaptian(java.lang.String,
// * com.oepl.document.OEPLMember)
// */
// @Override
// public void markAsCaptian(final int teamId, final OEPLMember member) {
//
// final Query query = new Query();
// query.addCriteria(Criteria.where("_id").is(teamId));
// final Update update = new Update();
// update.set("captian", member);
// mongoOperation.updateFirst(query, update, OEPLTeam.class);
// }
// }
