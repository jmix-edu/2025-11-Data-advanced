package com.company.jmixpmdata.listener;

import com.company.jmixpmdata.entity.Task;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskJpaListener {

    private static final Logger log = LoggerFactory.getLogger(TaskJpaListener.class);

    @PreUpdate
    @PreRemove
    @PrePersist
    public void beforeUpdate(Task task){
        log.info("{} before update {}", task.getClass().getSimpleName(), task.getId());
    }

    @PostLoad
    @PostUpdate
    @PostRemove
    @PostPersist
    public void afterUpdate(Task task){
        log.info("{} after update {}", task.getClass().getSimpleName(), task.getId());
    }
}
