package com.company.jmixpmdata.view.customsearch;


import com.company.jmixpmdata.entity.Project;
import com.company.jmixpmdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route(value = "custom-search-view", layout = MainView.class)
@ViewController(id = "CustomSearchView")
@ViewDescriptor(path = "custom-search-view.xml")
public class CustomSearchView extends StandardView {
    private static final Logger log = LoggerFactory.getLogger(CustomSearchView.class);
    @ViewComponent
    private CollectionContainer<Project> projectsDc;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        log.info("Before show - loaded projects:" + projectsDc.getItems().size());
    }
}