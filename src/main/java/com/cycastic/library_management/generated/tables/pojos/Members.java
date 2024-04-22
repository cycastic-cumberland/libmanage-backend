/*
 * This file is generated by jOOQ.
 */
package com.cycastic.library_management.generated.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Members implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String fullName;
    private LocalDateTime joinDate;

    public Members() {}

    public Members(Members value) {
        this.id = value.id;
        this.fullName = value.fullName;
        this.joinDate = value.joinDate;
    }

    public Members(
        String id,
        String fullName,
        LocalDateTime joinDate
    ) {
        this.id = id;
        this.fullName = fullName;
        this.joinDate = joinDate;
    }

    /**
     * Getter for <code>library_management.members.id</code>.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Setter for <code>library_management.members.id</code>.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for <code>library_management.members.full_name</code>.
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     * Setter for <code>library_management.members.full_name</code>.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter for <code>library_management.members.join_date</code>.
     */
    public LocalDateTime getJoinDate() {
        return this.joinDate;
    }

    /**
     * Setter for <code>library_management.members.join_date</code>.
     */
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Members other = (Members) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.fullName == null) {
            if (other.fullName != null)
                return false;
        }
        else if (!this.fullName.equals(other.fullName))
            return false;
        if (this.joinDate == null) {
            if (other.joinDate != null)
                return false;
        }
        else if (!this.joinDate.equals(other.joinDate))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.fullName == null) ? 0 : this.fullName.hashCode());
        result = prime * result + ((this.joinDate == null) ? 0 : this.joinDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Members (");

        sb.append(id);
        sb.append(", ").append(fullName);
        sb.append(", ").append(joinDate);

        sb.append(")");
        return sb.toString();
    }
}
