package kinokuniya.model;

import kinokuniya.enums.Stat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Subscription implements Serializable {
    @Id
    @Column(nullable = false)
    private int creatorID;

    @Id
    @Column(nullable = false)
    private int subscriberID;

    @Column(nullable = false)
    private String creatorName;

    @Column(nullable = false)
    private String subscriberName;

    @Column(nullable = false)
    private Stat status = Stat.PENDING;

    public Subscription() {
        // Do nothing
    }

    public Subscription(int creatorID, int subscriberID) {
        this.creatorID = creatorID;
        this.subscriberID = subscriberID;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public int getSubscriberID() {
        return subscriberID;
    }

    public void setSubscriberID(int subscriberID) {
        this.subscriberID = subscriberID;
    }

    public Stat getStatus() {
        return status;
    }

    public void setStatus(Stat status) {
        this.status = status;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    @Override
    public String toString() {
        return String.format("Subscribe{creatorName=%s, subscriberName=%s, status=%s}", creatorName, subscriberName, status);
    }
}