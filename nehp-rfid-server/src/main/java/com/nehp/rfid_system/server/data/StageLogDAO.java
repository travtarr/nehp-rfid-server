package com.nehp.rfid_system.server.data;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.StageLog;

import io.dropwizard.hibernate.AbstractDAO;


public class StageLogDAO extends AbstractDAO<StageLog> {

	public StageLogDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<StageLog> getById( Long id ) {
		StageLog log = get(id);
		if (log != null)
			return Optional.of(log);
		else
			return Optional.absent();
	}
	
	public Optional<List<StageLog>> getAll() {
		List<StageLog> list = list(namedQuery("stagelog.getAll"));
		if ( list == null || list.isEmpty() ){
			return Optional.absent();
		} else {
			return Optional.of(list);
		}
		//DetachedCriteria maxQuery = DetachedCriteria.forClass( StageLog.class );
	}
	
	public Optional<List<StageLog>> getByItem(Long item) {
		List<StageLog> list = list(namedQuery("stagelog.getByItem")
				.setParameter("item", item, LongType.INSTANCE));
		if ( list == null || list.isEmpty() ){
			return Optional.absent();
		} else {
			return Optional.of(list);
		}
	}
	
	public Long create(StageLog log) {
		log.setStageDate(new Date());
		return persist(log).getId();
	}
	
	public void update(StageLog log) {
		StageLog stageLog = get(log.getId());
		stageLog.setStageDate(log.getStageDate());
		stageLog.setItem(log.getItem());
		stageLog.setReason(log.getReason());
		stageLog.setStage(log.getStage());
		stageLog.setSignedBy(log.getSignedBy());
		stageLog.setDescription(log.getDescription());
		persist(stageLog);
	}
}
