package com.pd.finance.model.equity.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;

public class SummaryProfile {
    @JsonProperty("address1")
    private String address1;
    @JsonProperty("address2")
    private String address2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("country")
    private String country;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("fax")
    private String fax;
    @JsonProperty("website")
    private String website;
    @JsonProperty("industry")
    private String industry;
    @JsonProperty("sector")
    private String sector;
    @JsonProperty("longBusinessSummary")
    private String longBusinessSummary;

    @JsonProperty("fullTimeEmployees")
    @Field(targetType = FieldType.INT64)
    private Long fullTimeEmployees;

    @JsonProperty("companyOfficers")
    private List<Object> companyOfficers = new ArrayList<Object>();

    @JsonProperty("maxAge")
    @Field(targetType = FieldType.INT64)
    private Long maxAge;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getLongBusinessSummary() {
        return longBusinessSummary;
    }

    public void setLongBusinessSummary(String longBusinessSummary) {
        this.longBusinessSummary = longBusinessSummary;
    }

    public Long getFullTimeEmployees() {
        return fullTimeEmployees;
    }

    public void setFullTimeEmployees(Long fullTimeEmployees) {
        this.fullTimeEmployees = fullTimeEmployees;
    }

    public List<Object> getCompanyOfficers() {
        return companyOfficers;
    }

    public void setCompanyOfficers(List<Object> companyOfficers) {
        this.companyOfficers = companyOfficers;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }
}
