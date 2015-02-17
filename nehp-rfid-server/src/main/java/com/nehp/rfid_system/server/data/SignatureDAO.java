package com.nehp.rfid_system.server.data;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.Signature;

import io.dropwizard.hibernate.AbstractDAO;

public class SignatureDAO extends AbstractDAO<Signature> {
	

	public SignatureDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<Signature> getById(Long id) {
		return Optional.of(get(id));
	}

	public Optional<Signature> getByItemAndStage(Long item, Long stage){
		if ( verifyStage(stage) ) {
			List<Signature> sigList = list(namedQuery("signature.getByItemAndStage")
					.setParameter("item", item, LongType.INSTANCE)
					.setParameter("stage", stage, LongType.INSTANCE));
			if (sigList.size() > 0)
				return Optional.of(sigList.get(0));
			else
				return Optional.absent();
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
	
	
	private boolean verifyStage(Long stage){
		if (stage > 0 && stage < 8)
			return true;
		return false;
	}
}
