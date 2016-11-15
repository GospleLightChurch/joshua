package org.gyt.web.repository.repository;

import org.gyt.web.model.Message;
import org.gyt.web.model.MessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByOrderByIsReadAscCreatedDateDesc(Pageable pageable);

    Page<Message> findByTypeOrderByIsReadAscCreatedDateDesc(Pageable pageable, MessageType type);
}
