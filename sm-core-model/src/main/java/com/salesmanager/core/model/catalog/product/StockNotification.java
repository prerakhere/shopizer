package com.salesmanager.core.model.catalog.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@Table(name = "STOCK_NOTIFICATION",
    uniqueConstraints = @UniqueConstraint(columnNames = {"PRODUCT_ID", "EMAIL"}))
public class StockNotification extends SalesManagerEntity<Long, StockNotification> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "STOCK_NOTIF_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SUBSCRIBED_DATE", nullable = false)
    private Date subscribedDate;

    @Column(name = "NOTIFIED", nullable = false)
    private boolean notified = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NOTIFIED_DATE")
    private Date notifiedDate;

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getSubscribedDate() { return subscribedDate; }
    public void setSubscribedDate(Date subscribedDate) { this.subscribedDate = subscribedDate; }

    public boolean isNotified() { return notified; }
    public void setNotified(boolean notified) { this.notified = notified; }

    public Date getNotifiedDate() { return notifiedDate; }
    public void setNotifiedDate(Date notifiedDate) { this.notifiedDate = notifiedDate; }
}
