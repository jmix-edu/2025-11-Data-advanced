package com.company.jmixpmdata.view.user;

import com.company.jmixpmdata.app.UsersService;
import com.company.jmixpmdata.entity.Project;
import com.company.jmixpmdata.entity.User;
import com.company.jmixpmdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "users", layout = MainView.class)
@ViewController(id = "User.list")
@ViewDescriptor(path = "user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    private Project filterProject;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private GenericFilter genericFilter;

    @Install(to = "usersDl", target = Target.DATA_LOADER)
    private List<User> usersDlLoadDelegate(final LoadContext<User> loadContext) {
        LoadContext.Query query = loadContext.getQuery();
        if (filterProject != null && query != null) {
            return usersService.getUsersNotInProject(filterProject, query.getFirstResult(), query.getMaxResults());
//            List<User> allUsers = dataManager.load(User.class).all().list();
//           allUsers.removeAll(filterProject.getParticipants());
//           return allUsers;
        }

        return dataManager.loadList(loadContext);
    }

    public void setFilterProject(Project project) {
        this.filterProject = project;
        genericFilter.setVisible(false);
    }


}