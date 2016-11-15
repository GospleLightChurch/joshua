package org.gyt.web.api.service.impl;

import org.gyt.web.api.repository.PrimaryNavItemRepository;
import org.gyt.web.api.repository.SecondaryNavItemRepository;
import org.gyt.web.api.service.NavigationService;
import org.gyt.web.model.PrimaryNavItem;
import org.gyt.web.model.SecondaryNavItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavigationServiceImpl implements NavigationService {

    @Autowired
    private PrimaryNavItemRepository primaryNavItemRepository;

    @Autowired
    private SecondaryNavItemRepository secondaryNavItemRepository;

    @Override
    public List<PrimaryNavItem> getAll() {
        List<PrimaryNavItem> primaryNavItemList = primaryNavItemRepository.findAll();
        primaryNavItemList.sort((o1, o2) -> o1.getPosition().compareTo(o2.getPosition()));
        return primaryNavItemList;
    }

    @Override
    public boolean create(PrimaryNavItem navItem) {
        return null != primaryNavItemRepository.save(navItem);
    }

    @Override
    public boolean update(PrimaryNavItem navItem) {
        return create(navItem);
    }

    @Override
    public boolean remove(Long id) {
        if (primaryNavItemRepository.exists(id)) {
            primaryNavItemRepository.delete(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean add(SecondaryNavItem navItem, Long id) {
        PrimaryNavItem primaryNavItem = primaryNavItemRepository.findOne(id);

        if (null != primaryNavItem) {
            primaryNavItem.getChildren().add(navItem);
            secondaryNavItemRepository.save(navItem);
            update(primaryNavItem);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeSubItem(Long id) {
        SecondaryNavItem secondaryNavItem = secondaryNavItemRepository.findOne(id);

        if (null != secondaryNavItem) {
            secondaryNavItemRepository.delete(id);
        }

        return false;
    }
}
