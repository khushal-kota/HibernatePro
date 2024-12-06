package com.k.hiber;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="st_36")
public class Student {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
 int id;
 String name;
 String gender;
 String department;
 String program;
 @Column(name = "date_of_birth")
 String dob;
 @Column(name = "contact_number")
 String contact;
 @Column(name = "graduation_status")
 String graduation;
 double cpga;
 int backlogs;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getProgram() {
	return program;
}
public void setProgram(String program) {
	this.program = program;
}
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}
public String getContact() {
	return contact;
}
public void setContact(String contact) {
	this.contact = contact;
}
public String getGraduation() {
	return graduation;
}
public void setGraduation(String graduation) {
	this.graduation = graduation;
}
public double getCpga() {
	return cpga;
}
public void setCpga(double cpga) {
	this.cpga = cpga;
}
public int getBacklogs() {
	return backlogs;
}
public void setBacklogs(int backlogs) {
	this.backlogs = backlogs;
}
 
}
