package com.uva.bookings.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Booking")
public class Booking {

  @Id
  @GeneratedValue
  private Integer id;

  @Size(max = 50)
  private String guestName;

  @Column(unique = true)
  private int guestID;

  @Size(max = 50)
  private String guestEmail;

  private double price;
  private Integer units;
  private Integer numGuest;

  @Column(nullable = false)
  private Status status;
  private LocalDate dateIn;
  private LocalDate dateOut;

  @CreationTimestamp
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_date")
  private Date createdAt;

  @UpdateTimestamp
  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modify_date")
  private Date updatedAt;

  /**
   * Constructor
   */
  public Booking() {
  }

  public void modified() {
    setUpdatedAt(new Date());
  }

  public static void copyNonNullProperties(Object src, Object target) {
    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
  }

  private static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for (java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null)
        emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  // GETTERS & SETTERS
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getGuestName() {
    return guestName;
  }

  public void setGuestName(String guestName) {
    this.guestName = guestName;
  }

  public int getGuestID() {
    return guestID;
  }

  public void setGuestID(int guestID) {
    this.guestID = guestID;
  }

  public String getGuestEmail() {
    return guestEmail;
  }

  public void setGuestEmail(String guestEmail) {
    this.guestEmail = guestEmail;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getUnits() {
    return units;
  }

  public void setUnits(int units) {
    this.units = units;
  }

  public int getNumGuest() {
    return numGuest;
  }

  public void setNumGuest(int numGuest) {
    this.numGuest = numGuest;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public LocalDate getDateIn() {
    return dateIn;
  }

  public void setDateIn(LocalDate dateIn) {
    this.dateIn = dateIn;
  }

  public LocalDate getDateOut() {
    return dateOut;
  }

  public void setDateOut(LocalDate dateOut) {
    this.dateOut = dateOut;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date date) {
    this.updatedAt = date;
  }

}
