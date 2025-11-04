package com.company.jmixpmdata.view.project;


import com.company.jmixpmdata.datatype.ProjectLabels;
import com.company.jmixpmdata.entity.Project;
import com.company.jmixpmdata.entity.Roadmap;
import com.company.jmixpmdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "projects/:id", layout = MainView.class)
@ViewController(id = "Project.detail")
@ViewDescriptor(path = "project-detail-view.xml")
@EditedEntityContainer("projectDc")
public class ProjectDetailView extends StandardDetailView<Project> {
    @ViewComponent
    private DataContext dataContext;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private TypedTextField<ProjectLabels> labelsField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {
        Roadmap roadmap = dataContext.create(Roadmap.class);
        Project project = event.getEntity();
        project.setRoadmap(roadmap);

        labelsField.setReadOnly(false);
        project.setLabels(new ProjectLabels(List.of("bug", "task")));


    }
}