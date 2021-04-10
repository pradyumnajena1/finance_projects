package com.pd.finance.response.summary;
import java.util.HashMap;
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
        "raw",
        "fmt"
})
@Generated("jsonschema2pojo")
public class Estimate {

    @JsonProperty("raw")
    private Double raw;
    @JsonProperty("fmt")
    private String fmt;

    @JsonProperty("raw")
    public Double getRaw() {
        return raw;
    }

    @JsonProperty("raw")
    public void setRaw(Double raw) {
        this.raw = raw;
    }

    @JsonProperty("fmt")
    public String getFmt() {
        return fmt;
    }

    @JsonProperty("fmt")
    public void setFmt(String fmt) {
        this.fmt = fmt;
    }



}
