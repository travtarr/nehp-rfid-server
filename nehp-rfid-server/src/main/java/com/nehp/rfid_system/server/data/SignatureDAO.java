package com.nehp.rfid_system.server.data;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.Signature;

import io.dropwizard.hibernate.AbstractDAO;

public class SignatureDAO extends AbstractDAO<Signature> {
	
	private final String[] STAGES = { "MODELING", "KITTING", "MANUFACTURING",
			"QA/QC", "SHIPPED", "ARRIVAL", "INSTALLED", "STOPPED" };

	public SignatureDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<Signature> getById(Long id) {
		return Optional.of(get(id));
	}

	public Optional<Signature> getByItemAndStage(Long item, String stage){
		if ( verifyStage(stage) ) {
			return Optional.of(list(namedQuery("signature.getByItemAndStage")
					.setParameter("item", item, LongType.INSTANCE)
					.setParameter("stage", stage, StringType.INSTANCE)).get(0));
		}
		return Optional.absent();
	}
	
	public List<Signature> getAll(){
		return list(namedQuery("signature.getAll"));
	}
	
	public List<Signature> getAllByItem(Long id){
		return list(namedQuery("signature.getAllByItem").setParameter("item", id, LongType.INSTANCE));
	}
	
	public Long create(Signature sig){
		return persist(sig).getId();
	}
	
	
	private boolean verifyStage(String stage){
		for (int i = 0; i < STAGES.length; i++){
			if ( stage.equals(STAGES[i]) )
				return true;
		}
		return false;
	}
}
