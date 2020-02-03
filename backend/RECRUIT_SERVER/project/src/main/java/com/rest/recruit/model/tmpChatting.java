package com.rest.recruit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class tmpChatting {
    public String company;
    public String logo_url;

    public tmpChatting(String company, String logo_url) {
        this.company = company;
        this.logo_url = logo_url;
    }
}
