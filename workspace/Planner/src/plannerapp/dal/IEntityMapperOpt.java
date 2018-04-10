/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.dal;
import java.sql.ResultSet;
import java.util.Optional;

/**
 *
 * @author Dad
 */
@FunctionalInterface
public interface IEntityMapperOpt <T> {
    public Optional<T> map(ResultSet rs);
}
