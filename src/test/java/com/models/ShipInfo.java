package com.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipInfo {
    private String firstname;
    private String Lastname;
    private String zip;
}
