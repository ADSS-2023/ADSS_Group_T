package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

import javax.swing.text.html.parser.DTD;

public class NotificationsDTO extends DTO {

    private  String date;
    private String notification;

    public NotificationsDTO(String date, String notification) {
        super("Notifications");
        this.date = date;
        this.notification = notification;
    }

    public NotificationsDTO() {
        super("Notifications");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
