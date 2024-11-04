package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.GuideDTO;
import dat.entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class GuideDAO implements IDAO<GuideDTO, Long> {

    private static GuideDAO instance;
    private static EntityManagerFactory emf;

    public static GuideDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuideDAO();
        }
        return instance;
    }

    @Override
    public List<GuideDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<GuideDTO> query = em.createQuery("SELECT new dat.dtos.GuideDTO(g) FROM Guide g", GuideDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public GuideDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            return new GuideDTO(guide);
        }
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = new Guide(guideDTO);
            em.persist(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);
        }
    }

    @Override
    public GuideDTO update(Long id, GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            if (guide != null) {
                guide.setFirstname(guideDTO.getFirstname());
                guide.setLastname(guideDTO.getLastname());
                guide.setEmail(guideDTO.getEmail());
                guide.setPhone(guideDTO.getPhone());
                guide.setYearsOfExperience(guideDTO.getYearsOfExperience());
                Guide mergedGuide = em.merge(guide);
                em.getTransaction().commit();
                return new GuideDTO(mergedGuide);
            }
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            if (guide != null) {
                em.remove(guide);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Guide.class, id) != null;
        }
    }
}
