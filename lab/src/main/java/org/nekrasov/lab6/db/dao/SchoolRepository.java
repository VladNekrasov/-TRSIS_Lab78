/*
 * this code is available under GNU GPL v3
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package org.nekrasov.lab6.db.dao;

import org.nekrasov.lab6.db.model.School;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Pavel
 */
public interface SchoolRepository extends CrudRepository<School, Integer> {

    School findByNumber(Integer number);
}
