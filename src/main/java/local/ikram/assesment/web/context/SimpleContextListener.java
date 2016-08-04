package local.ikram.assesment.web.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import local.ikram.assesment.web.sp.MemoryDataStore;

public class SimpleContextListener implements ServletContextListener {

    private MemoryDataStore dataStore;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext();

        dataStore = MemoryDataStore.newInstance();
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext();

        dataStore = null;
    }
}
