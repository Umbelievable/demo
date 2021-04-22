package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "LibraryItem")
@DynamicInsert
@DynamicUpdate

@AttributeOverride(name="thisCateNo", column=@Column(name="librarycate_no"))
public class LibraryItem extends Item implements Serializable {

}