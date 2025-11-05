package com.company.jmixpmdata.app;

import com.company.jmixpmdata.entity.Project;
import io.jmix.core.DataManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProjectService {
    private final DataManager dataManager;

    public ProjectService(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    public void save(@NotNull @Valid Project project) {
        dataManager.save(project, project.getRoadmap());
    }
}