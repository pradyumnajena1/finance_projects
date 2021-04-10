
package com.pd.finance.response.summary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "address1",
    "address2",
    "city",
    "zip",
    "country",
    "phone",
    "fax",
    "website",
    "industry",
    "sector",
    "longBusinessSummary",
    "fullTimeEmployees",
    "companyOfficers",
    "maxAge"
})
@Generated("jsonschema2pojo")
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
    private Long fullTimeEmployees;
    @JsonProperty("companyOfficers")
    private List<Object> companyOfficers = new ArrayList<Object>();
    @JsonProperty("maxAge")
    private Long maxAge;

    @JsonProperty("address1")
    public String getAddress1() {
        return address1;
    }

    @JsonProperty("address1")
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @JsonProperty("address2")
    public String getAddress2() {
        return address2;
    }

    @JsonProperty("address2")
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("zip")
    public String getZip() {
        return zip;
    }

    @JsonProperty("zip")
    public void setZip(String zip) {
        this.zip = zip;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("fax")
    public String getFax() {
        return fax;
    }

    @JsonProperty("fax")
    public void setFax(String fax) {
        this.fax = fax;
    }

    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
    }

    @JsonProperty("industry")
    public String getIndustry() {
        return industry;
    }

    @JsonProperty("industry")
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @JsonProperty("sector")
    public String getSector() {
        return sector;
    }

    @JsonProperty("sector")
    public void setSector(String sector) {
        this.sector = sector;
    }

    @JsonProperty("longBusinessSummary")
    public String getLongBusinessSummary() {
        return longBusinessSummary;
    }

    @JsonProperty("longBusinessSummary")
    public void setLongBusinessSummary(String longBusinessSummary) {
        this.longBusinessSummary = longBusinessSummary;
    }

    @JsonProperty("fullTimeEmployees")
    public Long getFullTimeEmployees() {
        return fullTimeEmployees;
    }

    @JsonProperty("fullTimeEmployees")
    public void setFullTimeEmployees(Long fullTimeEmployees) {
        this.fullTimeEmployees = fullTimeEmployees;
    }

    @JsonProperty("companyOfficers")
    public List<Object> getCompanyOfficers() {
        return companyOfficers;
    }

    @JsonProperty("companyOfficers")
    public void setCompanyOfficers(List<Object> companyOfficers) {
        this.companyOfficers = companyOfficers;
    }

    @JsonProperty("maxAge")
    public Long getMaxAge() {
        return maxAge;
    }

    @JsonProperty("maxAge")
    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }


}
