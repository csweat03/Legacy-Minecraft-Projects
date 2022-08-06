package com.fbiclient.fbi.client.events.entity;

import me.xx.api.event.Event;

/**
 * @author Kyle
 * @since 5/6/2018
 **/
public class EntityMouseOverEvent extends Event {

    double distance;

    public EntityMouseOverEvent(double distance) {
        this.distance = distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

}
