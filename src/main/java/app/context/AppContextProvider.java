package app.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

// TODO: Auto-generated Javadoc
/**
 * The Class AppContextProvider.
 */
public final class AppContextProvider {

	/** The app context. */
	private static ApplicationContext appContext;

	/** The instance. */
	private static AppContextProvider instance;

	
	/**
	 * Instantiates a new app context provider.
	 */
	private AppContextProvider() {
		appContext = new FileSystemXmlApplicationContext(
				"/src/main/webapp/WEB-INF/config/application-context.xml");
		registerShutdownHook((ConfigurableApplicationContext) appContext);
	}

	/**
	 * Gets the single instance of AppContextProvider.
	 *
	 * @return single instance of AppContextProvider
	 */
	public static AppContextProvider getInstance() {
		synchronized (AppContextProvider.class) {
			if (instance == null) {
				instance = new AppContextProvider();
			}
		}
		return instance;
	}

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */
	public ApplicationContext getApplicationContext() {
		return appContext;

	}

	/**
	 * Gets the bean.
	 *
	 * @param beanName the bean name
	 * @return the bean
	 */
	public Object getBean(final String beanName) {
		return appContext.getBean(beanName);
	}

	/**
	 * Gets the bean.
	 *
	 * @param <T> the generic type
	 * @param className the class name
	 * @return the bean
	 */
	public <T> T getBean(final Class<T> className) {
		return appContext.getBean(className);
	}

	/**
	 * Register shutdown hook.
	 *
	 * @param context the context
	 */
	public void registerShutdownHook(
			final ConfigurableApplicationContext context) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				context.close();
			}
		});
	}

}