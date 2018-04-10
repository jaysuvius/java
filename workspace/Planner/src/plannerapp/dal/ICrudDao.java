/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.dal;

import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author Dad
 */
public interface ICrudDao<T> {
    Optional<T> getById(int id);
    Collection<Optional<T>> getByFields(T entity);
    Collection<T> getAll();
    boolean insert(T entity);
    boolean update(T entity);
    boolean deleteById(int id);
    int getNextId();
}
