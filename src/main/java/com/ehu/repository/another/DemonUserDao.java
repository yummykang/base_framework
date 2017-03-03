package com.ehu.repository.another;

import com.ehu.entity.another.DemonUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * write something to describe this file.
 *
 * @author demon
 * @since 2017-03-03 11:05.
 */
public interface DemonUserDao extends JpaRepository<DemonUser, Integer>, JpaSpecificationExecutor<DemonUser> {
}
