package org.gyt.web.api.repository;

import org.gyt.web.model.PrimaryNavItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 一级导航栏仓库
 * Created by y27chen on 2016/9/14.
 */
@Repository
@Transactional
public interface PrimaryNavItemRepository extends JpaRepository<PrimaryNavItem, Long> {
}
