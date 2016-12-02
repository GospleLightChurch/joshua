package org.gyt.web.repository.repository;

import org.gyt.web.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
