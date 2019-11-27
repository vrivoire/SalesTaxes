package com.teksystems.salestaxes.model;

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
@Table(name = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 7144791867622755443L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String description;

    @Basic(optional = false)
    @Column(name = "IS_IMPORTED", nullable = false)
    private Boolean isImported;

    @Basic(optional = false)
    @Column(name = "IS_TAXABLE", nullable = false)
    private Boolean isTaxable;

    @Basic(optional = false)
    @Column(nullable = false)
    private float price;

    /**
     * Holds item information
     */
    public Item() {
    }

    /**
     *
     * @param name
     * @param description
     * @param isImported
     * @param isTaxable
     * @param price
     */
    public Item(String name, String description, Boolean isImported, Boolean isTaxable, float price) {
        this.name = name;
        this.description = description;
        this.isImported = isImported;
        this.isTaxable = isTaxable;
        this.price = price;
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
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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

    /**
     *
     * @return
     */
    public Boolean isTaxable() {
        return isTaxable;
    }

    /**
     *
     * @param isTaxable
     */
    public void setIsTaxable(Boolean isTaxable) {
        this.isTaxable = isTaxable;
    }

    /**
     *
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item [");
        builder.append("id=").append(id);
        builder.append(", name=").append(name);
        builder.append(", description=").append(description);
        builder.append(", isImported=").append(isImported);
        builder.append(", isTaxable=").append(isTaxable);
        builder.append(", price=").append(price);
        builder.append("]");
        return builder.toString();
    }

}
