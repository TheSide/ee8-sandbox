/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hantsylabs.example.ee8.jsf;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.enterprise.event.NotificationOptions;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author hantsy
 */
@ViewScoped
@Named("eventBean")
public class EventBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(EventBean.class.getName());

    @Inject
    Event<Message> event;

    private String message;
    
    private String notification;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
    
   
    public void fireEvent() {
        LOG.log(Level.INFO, "fire event async...");
        event.fireAsync(new Message(this.message))
                .thenAccept((m) -> this.setNotification(m+ " was sent")); 
    }

    public void fireEventOptions() {
        LOG.log(Level.INFO, "fire event async...");
        event.fireAsync(
                new Message(this.message),
                NotificationOptions.builder()
                        .set("weld.async.notification.timeout", 1000)
                        .build()
        );
    }
}
