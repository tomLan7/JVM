package javaѧϰ;
import java.util.concurrent.TimeUnit;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Quartz���� {
	class Pj implements Job{

		@Override
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			// TODO �Զ����ɵķ������
			System.out.println("���Զ�ʱ��");
		}
		
	}
	public static void main(String[] args) throws SchedulerException, InterruptedException {
		// TODO �Զ����ɵķ������
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

		  Scheduler sched = schedFact.getScheduler();

		  sched.start();

		  // define the job and tie it to our HelloJob class
		  JobDetail job = newJob(Pj.class)
		      .withIdentity("myJob", "group1")
		      .build();

		  // Trigger the job to run now, and then every 40 seconds
		  Trigger trigger = newTrigger()
		      .withIdentity("myTrigger", "group1")
		      .startNow()
		      .withSchedule(simpleSchedule()
		          .withIntervalInSeconds(40)
		          .repeatForever())
		      .build();

		  // Tell quartz to schedule the job using our trigger
		  sched.scheduleJob(job, trigger);
	}

}
