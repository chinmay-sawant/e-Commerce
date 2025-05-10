package com.learn.ecom.ex.exHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultException   
{
    public String errorCode;
    public String errorMessage;
    public String errorDescription;
    public String errEndpoint;
}
