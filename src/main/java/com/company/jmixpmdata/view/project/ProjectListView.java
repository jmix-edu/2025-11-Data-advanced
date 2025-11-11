package com.company.jmixpmdata.view.project;

import com.company.jmixpmdata.datatype.ProjectLabels;
import com.company.jmixpmdata.entity.Project;
import com.company.jmixpmdata.entity.Roadmap;
import com.company.jmixpmdata.entity.User;
import com.company.jmixpmdata.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;


@Route(value = "projects", layout = MainView.class)
@ViewController(id = "Project.list")
@ViewDescriptor(path = "project-list-view.xml")
@LookupComponent("projectsDataGrid")
@DialogMode(width = "64em")
public class ProjectListView extends StandardListView<Project> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private CollectionContainer<Project> projectsDc;

    @Subscribe(id = "createWithDM", subject = "clickListener")
    public void onCreateWithDMClick(final ClickEvent<JmixButton> event) {
        Project project = dataManager.create(Project.class);
        project.setName("Project " + RandomStringUtils.randomAlphabetic(5));

        final User user = (User) currentAuthentication.getUser();
        project.setManager(user);
        Roadmap roadmap = dataManager.create(Roadmap.class);
        roadmap.setStartDate(LocalDate.now());
        project.setRoadmap(roadmap);

        project.setLabels(new ProjectLabels(List.of("1", "2", "3")));

        Project saved = unconstrainedDataManager.save(project, roadmap).get(project);

        projectsDc.getMutableItems().add(saved);



    }
}