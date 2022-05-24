/*
package com.alddeul.capstone;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "role",
        "id",
        "username",
        "email"
})

public class userinfo {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String name;

    @JsonProperty("role")
    private String role;

    @JsonProperty("email")
    private String email;

    @JsonProperty("id")

    public Long getId() {
        return id;
    }
    @JsonProperty("id")

    public void setId(Long id) {
        this.id = id;
    }
    @JsonProperty("email")

    public String getEmail() {
        return email;
    }
    @JsonProperty("email")

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }
    @JsonProperty("name")
    public String getName() {
        return name;
    }
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

}*/
