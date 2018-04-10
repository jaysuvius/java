/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.dal;
import java.sql.ResultSet;

/**
 *
 * @author Dad
 */
@FunctionalInterface
public interface IEntityMapper <T> {
    public T map(ResultSet rs);
}
