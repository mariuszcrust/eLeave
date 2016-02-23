package com.company.eleave.leave.repository;

import com.company.eleave.leave.entity.LeaveType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastian Szlachetka
 */
@Repository
public interface LeaveTypeRepository extends CrudRepository<LeaveType, Long> {

}
