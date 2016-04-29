package com.eda.rest.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.eda.rest.job.AutoAllocationJob;


@Configuration
public class QuartzConfig /*implements ServletContextListener*/{

	
	@Autowired
	private ApplicationContext applicationContext;
	SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

	@Bean
	public SchedulerFactoryBean quartzScheduler() {

		quartzScheduler.setQuartzProperties(quartzProperties());
		// quartzScheduler.setDataSource(dataSource);
		// quartzScheduler.setTransactionManager(transactionManager);
		quartzScheduler.setOverwriteExistingJobs(true);
		
		// Custom job factory of spring with DI support for @Autowired
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		quartzScheduler.setJobFactory(jobFactory);

		Trigger[] triggers = { processMyJobTrigger().getObject() };

		quartzScheduler.setTriggers(triggers);

		
		return quartzScheduler;
	}

	@Bean
	public JobDetailFactoryBean processMyJob() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(AutoAllocationJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean
	// Configure cron to fire trigger every 1 minute
	public CronTriggerFactoryBean processMyJobTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(processMyJob().getObject());
		cronTriggerFactoryBean.setCronExpression("0/10 * * * * ?");
		return cronTriggerFactoryBean;
	}

	@Bean
	public Properties quartzProperties() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource(
				"quartz.properties"));
		Properties properties;

		try {
			propertiesFactoryBean.afterPropertiesSet();
			properties = propertiesFactoryBean.getObject();
		} catch (IOException e) {
			throw new RuntimeException("Unable to load quartz.properties", e);
		}

		return properties;
	}

	/*@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			quartzScheduler.getScheduler().shutdown();
			System.out.println("SCHEDULER IS SHUTDOWN::::::::");
		} catch (SchedulerException e) {
			
			e.printStackTrace();
		}
		
	}*/
}
