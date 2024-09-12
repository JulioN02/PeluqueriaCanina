package persistence;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logic.Dueño;
import persistence.exceptions.NonexistentEntityException;
public class DueñoJpaController implements Serializable {

    
    public DueñoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;
    
    public DueñoJpaController(){
        emf = Persistence.createEntityManagerFactory("pcPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dueño dueño) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dueño);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dueño dueño) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dueño = em.merge(dueño);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = dueño.getId_dueño();
                if (findDueño(id) == null) {
                    throw new NonexistentEntityException("The due\u00f1o with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dueño dueño;
            try {
                dueño = em.getReference(Dueño.class, id);
                dueño.getId_dueño();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The due\u00f1o with id " + id + " no longer exists.", enfe);
            }
            em.remove(dueño);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dueño> findDueñoEntities() {
        return findDueñoEntities(true, -1, -1);
    }

    public List<Dueño> findDueñoEntities(int maxResults, int firstResult) {
        return findDueñoEntities(false, maxResults, firstResult);
    }

    private List<Dueño> findDueñoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dueño.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Dueño findDueño(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dueño.class, id);
        } finally {
            em.close();
        }
    }

    public int getDueñoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dueño> rt = cq.from(Dueño.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
