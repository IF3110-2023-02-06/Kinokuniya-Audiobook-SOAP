package kinokuniya.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Logging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int ID;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(nullable = false, length = 16)
    private String IP;

    @Column(nullable = false)
    private String endpoint;

    @Column(nullable = false)
    private Date requestedAt;

    public Logging() {
        // Do nothing
    }

    public int getID() {
        return this.ID;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIP() {
        return this.IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Date getRequestedAt() {
        return this.requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }
}
