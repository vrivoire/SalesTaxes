package com.vrivoire.salestaxes.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "tax")
public class Tax implements Serializable {

    private static final long serialVersionUID = 7144791867622755443L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private float rate;

    @Basic(optional = false)
    @Column(name = "IS_IMPORTED", nullable = false)
    private Boolean isImported;

    /**
     * Holds tax information
     */
    public Tax() {
    }

    /**
     *
     * @param id
     * @param name
     * @param rate
     * @param isImported
     */
    public Tax(String name, float rate, Boolean isImported) {
        this.name = name;
        this.rate = rate;
        this.isImported = isImported;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public float getRate() {
        return rate;
    }

    /**
     *
     * @param rate
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

    /**
     *
     * @return
     */
    public Boolean isImported() {
        return isImported;
    }

    /**
     *
     * @param isImported
     */
    public void setIsImported(Boolean isImported) {
        this.isImported = isImported;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tax)) {
            return false;
        }
        Tax other = (Tax) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Tax [");
        builder.append("id=").append(id);
        builder.append(", isImported=").append(isImported);
        builder.append(", name=").append(name);
        builder.append(", rate=").append(rate);
        builder.append("]");
        return builder.toString();
    }

}
