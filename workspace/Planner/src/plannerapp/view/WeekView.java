/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerapp.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import plannerapp.bl.AppointmentBo;
import plannerapp.model.Appointment;

/**
 *
 * @author Dad
 */
public class WeekView {
    private final ObjectProperty<LocalDate> week = new SimpleObjectProperty<>();
    
    private final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());
    
    private final BorderPane view ;
    private final GridPane calendar ;
    private final AppointmentBo aptBo = new AppointmentBo();
    private Planner mainApp;

    public void setMainApp(Planner ma){
        mainApp = ma;
    }
    
    public WeekView(LocalDate week, Locale l) {
        view = new BorderPane();
        view.getStyleClass().add("calendar");
        calendar = new GridPane();
        calendar.getStyleClass().add("calendar-grid");

        Label header = new Label();
        header.setMaxWidth(Double.MAX_VALUE);
        header.getStyleClass().add("calendar-header");
        
        this.week.addListener((obs, oldWeek, newWeek) -> 
            rebuildCalendar());
        
        this.locale.addListener((obs, oldLocale, newLocale) -> 
            rebuildCalendar());
        
        view.setTop(header);
        view.setCenter(calendar);
        
        view.getStylesheets().add(getClass().getResource("calendar.css").toExternalForm());
        
        setWeek(week);
        

            header.textProperty().bind(Bindings.createStringBinding(() -> 
            this.week.get().format(DateTimeFormatter.ofPattern("MMMM yyyy", l)), 
            this.week, this.locale));

    }
    
    private List<Appointment> getAppointments(LocalDate start, LocalDate end){
        Collection<Appointment> apts = aptBo.getByDateRange(start.atStartOfDay(TimeZone.getDefault().toZoneId()), end.atStartOfDay(TimeZone.getDefault().toZoneId()));
        List<Appointment> aptList = new ArrayList<>();
        for (Object a : apts){
            Appointment apt;
            if (a instanceof Optional){
                apt = (Appointment)((Optional)a).get();
            }
            else{
                apt = (Appointment)a;
            }
            aptList.add(apt);
        }
        return aptList;
    }
    
    public WeekView(Planner mainApp, Locale l) {
        this(LocalDate.now(), l) ;
        setMainApp(mainApp);
        setLocale(l);
    }
    
    public void nextWeek() {
        week.set(week.get().plusWeeks(1));
    }
    
    public void previousWeek() {
        week.set(week.get().minusWeeks(1));
    }
    
    private void rebuildCalendar() {
        
        calendar.getChildren().clear();
        
        WeekFields weekFields = WeekFields.of(locale.get());
        
        LocalDate first = week.get();
        
        int dayOfWeekOfFirst = first.get(weekFields.dayOfWeek()) ;
        
        // column headers:
        for (int dayOfWeek = 1 ; dayOfWeek <= 7 ; dayOfWeek++) {
            LocalDate date = first.minusDays(dayOfWeekOfFirst - dayOfWeek);
            DayOfWeek day = date.getDayOfWeek() ;
            Label label = new Label(day.getDisplayName(TextStyle.SHORT_STANDALONE, locale.get()));
            label.getStyleClass().add("calendar-day-header");
            GridPane.setHalignment(label, HPos.CENTER);
            calendar.add(label, dayOfWeek - 1, 0);
        }
        
        LocalDate firstDisplayedDate = first.minusDays(dayOfWeekOfFirst - 1);
        LocalDate last =firstDisplayedDate;
        
        int dayOfWeekOfLast = last.get(weekFields.dayOfWeek());
        LocalDate lastDisplayedDate = last.plusDays(7 - dayOfWeekOfLast);
        
        List<Appointment> appointments = getAppointments(firstDisplayedDate, lastDisplayedDate);
        
        PseudoClass beforeWeek = PseudoClass.getPseudoClass("before-display-week");
        PseudoClass afterWeek = PseudoClass.getPseudoClass("after-display-week");        
        for (LocalDate date = firstDisplayedDate ; ! date.isAfter(lastDisplayedDate) ; date = date.plusDays(1)) {
            Label label = new Label(String.valueOf(date.getDayOfMonth()));
            label.getStyleClass().add("calendar-cell");
            label.pseudoClassStateChanged(beforeWeek, date.isBefore(first));
            label.pseudoClassStateChanged(afterWeek, date.isAfter(last));

            GridPane.setHalignment(label, HPos.CENTER);
            
            int dayOfWeek = date.get(weekFields.dayOfWeek()) ;
            int daysSinceFirstDisplayed = (int) firstDisplayedDate.until(date, ChronoUnit.DAYS);
            int weeksSinceFirstDisplayed = daysSinceFirstDisplayed / 7 ;
            calendar.add(label, dayOfWeek - 1, weeksSinceFirstDisplayed + 1);
            Label aptLabel = new Label();
            StringBuilder sb = new StringBuilder();
            for (Appointment a : appointments){
                if (a.getStart().toLocalDate().equals(date) || (a.getStart().toLocalDate().isBefore(date) && a.getEnd().toLocalDate().isAfter(date)) || a.getEnd().toLocalDate().equals(date)){
                    sb.append(a.getTitle());
                    sb.append("\n");
                    aptLabel.setText(sb.toString());
                }
            }
            calendar.add(aptLabel, dayOfWeek - 1, weeksSinceFirstDisplayed + 1);
        }
    }
    
    
    
    public Node getView() {
        return view ;
    }

    public final ObjectProperty<LocalDate> weekProperty() {
        return this.week;
    }
    

    public final LocalDate getWeek() {
        return this.weekProperty().get();
    }
    

    public final void setWeek(final LocalDate week) {
        this.weekProperty().set(week);
    }

    public final ObjectProperty<Locale> localeProperty() {
        return this.locale;
    }
    

    public final java.util.Locale getLocale() {
        return this.localeProperty().get();
    }
    

    public final void setLocale(final java.util.Locale locale) {
        this.localeProperty().set(locale);
    }
}
