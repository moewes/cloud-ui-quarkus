package net.moewes.cloudui.quarkus.runtime;

import java.util.List;
import java.util.logging.Logger;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.runtime.annotations.Recorder;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import net.moewes.cloudui.quarkus.runtime.repository.View;

@Recorder
public class CloudUiRecorder {

    private static final Logger log = Logger.getLogger(CloudUiRecorder.class.getName());
    
    public void touch(BeanContainer beanContainer, List<String> scripts) {
        log.info("register scripts ");
        HtmlPageBuilder pageBuilder = beanContainer.instance(HtmlPageBuilder.class);
        pageBuilder.setScripts(scripts);
    }

    public Handler<RoutingContext> getPageHandler(BeanContainer beanContainer) {

        return new PageHandler(beanContainer);
    }

    public Handler<RoutingContext> getViewHandler(BeanContainer beanContainer) {
        return new ViewRequestHandler(beanContainer, Thread.currentThread().getContextClassLoader());
    }

    public void registerView(BeanContainer beanContainer, View view) {
        CloudUiRouter router = beanContainer.instance(CloudUiRouter.class);
        router.addView(view);
    }
}
