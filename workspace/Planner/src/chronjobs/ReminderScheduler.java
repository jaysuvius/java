/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronjobs;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import plannerapp.model.Appointment;

/**
 *
 * @author Dad
 */
public class ReminderScheduler {

      public ReminderScheduler(long aInitialDelay, long aDelayBetweenBeeps, long aStopAfter){
        fInitialDelay = aInitialDelay;
        fDelayBetweenRuns = aDelayBetweenBeeps;
        fShutdownAfter = aStopAfter;
        fScheduler = Executors.newScheduledThreadPool(NUM_THREADS);    
      }

      /** Sound the alarm for a few seconds, then stop. */
      public void activateAlarmThenStop(Appointment appt, ResourceBundle rb){
        Runnable soundAlarmTask = new SoundAlarmTask(appt, rb);
        ScheduledFuture<?> soundAlarmFuture = fScheduler.scheduleWithFixedDelay(
          soundAlarmTask, fInitialDelay, fDelayBetweenRuns, TimeUnit.SECONDS
        );
        Runnable stopAlarm = new StopAlarmTask(soundAlarmFuture);
        fScheduler.schedule(stopAlarm, fShutdownAfter, TimeUnit.SECONDS);
      }

      // PRIVATE 
      private final ScheduledExecutorService fScheduler;
      private final long fInitialDelay;
      private final long fDelayBetweenRuns;
      private final long fShutdownAfter;

      /** If invocations might overlap, you can specify more than a single thread.*/ 
      private static final int NUM_THREADS = 1;
      private static final boolean DONT_INTERRUPT_IF_RUNNING = false;

      private static final class SoundAlarmTask implements Runnable {
        private Appointment appt;
        private ResourceBundle rb;
        public void setApppointment(Appointment appt){
            this.appt = appt;
        }
        public SoundAlarmTask(Appointment appt, ResourceBundle rb){
            this.appt = appt;
            this.rb = rb;
        }
        @Override public void run() {
            Platform.runLater(() -> {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setTitle("Reminder");
                a.setContentText(rb.getString("appointment") + " " + appt.getTitle() + " " + appt.getStart().toLocalDateTime().toString());
                a.show();
            });
        }
      }

      private final class StopAlarmTask implements Runnable {
        StopAlarmTask(ScheduledFuture<?> aSchedFuture){
          fSchedFuture = aSchedFuture;
        }
        @Override public void run() {
          fSchedFuture.cancel(DONT_INTERRUPT_IF_RUNNING);
          fScheduler.shutdown();
        }
        private ScheduledFuture<?> fSchedFuture;
      }
}
