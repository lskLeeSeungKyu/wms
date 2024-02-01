package com.server.boot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitsDTO {
       
    private String user_id;     
    private int restaurant_id; 
    private String visit_date;  
    private int taste_rating;   
}
