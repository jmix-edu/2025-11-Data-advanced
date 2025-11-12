package com.company.jmixpmdata.repository;

import com.company.jmixpmdata.entity.Task;
import io.jmix.core.repository.JmixDataRepository;

import java.util.UUID;

public interface TaskRepository extends JmixDataRepository<Task, UUID> {
}