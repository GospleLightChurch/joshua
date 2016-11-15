package org.gyt.web.api.service;

import org.gyt.web.model.PrimaryNavItem;
import org.gyt.web.model.SecondaryNavItem;

import java.util.List;

/**
 * 导航栏API，提供一下接口
 * - 获取所有一级导航
 * - 创建一级导航
 * - 修改一级导航
 * - 删除一级导航
 * - 创建二级导航
 * - 修改二级导航
 * - 删除二级导航
 * Created by y27chen on 2016/9/20.
 */
public interface NavigationService {

    List<PrimaryNavItem> getAll();

    boolean create(PrimaryNavItem navItem);

    boolean update(PrimaryNavItem navItem);

    boolean remove(Long id);

    boolean add(SecondaryNavItem navItem, Long id);

    boolean removeSubItem(Long id);
}
