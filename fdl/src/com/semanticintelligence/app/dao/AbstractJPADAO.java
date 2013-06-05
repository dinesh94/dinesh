/**
 * 
 */

package com.semanticintelligence.app.dao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

/**
 * @see com.semanticintelligence.app.dao.GenericJPADAO
 * 
 * @author JRodriguez
 * 
 */
public abstract class AbstractJPADAO<T extends Object> implements GenericJPADAO<T> {
	private int defaultMaxResults = DEFAULT_MAX_RESULTS;

	/**
	 * The {@link EntityManager} which is used by all query manipulation and
	 * execution in this DAO.
	 * 
	 * @return the {@link EntityManager}
	 */
	public abstract EntityManager getEntityManager();

	@Transactional
	public T store(T toStore) {
		toStore = persist(toStore);
		flush();
		return toStore;
	}

	@Transactional
	public T merge(T toMerge) {
		toMerge = getEntityManager().merge(toMerge);
		flush();

		return toMerge;
	}

	@Transactional
	public T persist(T toPersist) {
		getEntityManager().persist(toPersist);
		flush();
		return toPersist;
	}

	@Transactional
	public void remove(Object toRemove) {
		toRemove = getEntityManager().merge(toRemove);
		getEntityManager().remove(toRemove);
		flush();
	}

	@Transactional
	public void flush() {
		getEntityManager().flush();
	}

	@Transactional
	public void refresh(Object o) {
		try {
			if (o != null) {
				if (o instanceof java.util.Collection) {
					for (Iterator<?> i = ((Collection<?>) o).iterator(); i.hasNext();) {
						try {
							refresh(i.next());
						} catch (EntityNotFoundException x) {
							// This entity has been deleted - remove it from the collection
							i.remove();
						}
					}
				} else {
					//if (getTypes().contains(o.getClass())) {
						getEntityManager().refresh(o);
					//}
				}
			}
		} catch (EntityNotFoundException x) {
			// This entity has been deleted
		}
	}

	@Transactional
	public void setDefaultMaxResults(int defaultMaxResults) {
		this.defaultMaxResults = defaultMaxResults;
	}

	@Transactional
	public int getDefaultMaxResults() {
		return defaultMaxResults;
	}

	@Transactional
	public T executeQueryByNameSingleResult(String queryName) {
		return executeQueryByNameSingleResult(queryName, (Object[]) null);

	}

	@Transactional
	@SuppressWarnings("unchecked")
	public T executeQueryByNameSingleResult(String queryName, Object... parameters) {
		Query query = createNamedQuery(queryName, DEFAULT_FIRST_RESULT_INDEX, 1, parameters);
		return (T) query.getSingleResult();
	}

	@Transactional
	public List<T> executeQueryByName(String queryName) {
		return executeQueryByName(queryName, DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults());
	}

	@Transactional
	public List<T> executeQueryByName(String queryName, Integer firstResult, Integer maxResults) {
		return executeQueryByName(queryName, firstResult, maxResults, (Object[]) null);
	}

	@Transactional
	public List<T> executeQueryByName(String queryName, Object... parameters) {
		return executeQueryByName(queryName, DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults(), parameters);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> executeQueryByName(String queryName, Integer firstResult, Integer maxResults, Object... parameters) {
		Query query = createNamedQuery(queryName, firstResult, maxResults, parameters);
		return query.getResultList();
	}

	@Transactional
	public Query createNamedQuery(String queryName, Integer firstResult, Integer maxResults) {
		return createNamedQuery(queryName, firstResult, maxResults, (Object[]) null);
	}

	@Transactional
	public Query createNamedQuery(String queryName, Integer firstResult, Integer maxResults, Object... parameters) {
		Query query = getEntityManager().createNamedQuery(queryName);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i + 1, parameters[i]);
			}
		}

		query.setFirstResult(firstResult == null || firstResult < 0 ? DEFAULT_FIRST_RESULT_INDEX : firstResult);
		if (maxResults != null && maxResults > 0)
			query.setMaxResults(maxResults);

		return query;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> executeQuery(String queryString, Integer firstResult, Integer maxResults, Object... parameters) {
		Query query = createQuery(queryString, firstResult, maxResults, parameters);
		return query.getResultList();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> executeQuery(String queryString, Object... parameters) {
		Query query = createQuery(queryString, DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults(), parameters);
		return query.getResultList();
	}

	@Transactional
	public Object executeQuerySingleResult(String queryString) {
		return executeQuerySingleResult(queryString, (Object[]) null);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public T executeQuerySingleResult(String queryString, Object... parameters) {
		Query query = createQuerySingleResult(queryString, parameters);
		return (T) query.getSingleResult();
	}

	@Transactional
	public Query createQuery(String queryString, Integer firstResult, Integer maxResults) {
		return createQuery(queryString, firstResult, maxResults, (Object[]) null);
	}

	/**
	 * Creates a query that will return a single result by default
	 * 
	 * @param queryString
	 * @param parameters
	 * @return
	 */
	public Query createQuerySingleResult(String queryString, Object... parameters) {
		return createQuery(queryString, DEFAULT_FIRST_RESULT_INDEX, 1, parameters);
	}

	@Transactional
	public Query createQuery(String queryString, Integer firstResult, Integer maxResults, Object... parameters) {
		Query query = getEntityManager().createQuery(queryString);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i + 1, parameters[i]);
			}
		}

		/*query.setFirstResult(firstResult == null || firstResult < 0 ? DEFAULT_FIRST_RESULT_INDEX : firstResult);
		if (maxResults != null && maxResults > 0)
			query.setMaxResults(maxResults);*/

		return query;
	}

}
