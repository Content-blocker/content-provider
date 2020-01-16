package com.kumuluzee.blocker.provider.ContentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class ContentService {
    @PersistenceContext
    private EntityManager em;

    public List<ContentEntity> getContentEntities() {
        List<ContentEntity> contentEntities = em
                .createNamedQuery("ContentEntity.selectAll", ContentEntity.class)
                .getResultList();

        return contentEntities;
    }

    @Transactional
    public void addEntity(ContentEntity contentEntity) {
        if (contentEntity != null) {
            em.getTransaction().begin();
            em.persist(contentEntity);
            em.getTransaction().commit();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteEntity(String id) {
        ContentEntity entity = em.find(ContentEntity.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

}
