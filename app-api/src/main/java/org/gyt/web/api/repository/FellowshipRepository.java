package org.gyt.web.api.repository;

import org.gyt.web.model.Fellowship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 团契仓库
 * Created by y27chen on 2016/9/14.
 */
@Repository
@Transactional
public interface FellowshipRepository extends JpaRepository<Fellowship, String> {
}
