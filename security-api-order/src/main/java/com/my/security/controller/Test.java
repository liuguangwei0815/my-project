package com.my.security.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class Test {
	
	public static void main(String[] args) throws InterruptedException {
		
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders header = new HttpHeaders();
		
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
		header.add("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIiwiZ2V0ZXdheSJdLCJ1c2VyX25hbWUiOiIxMjMxMjMiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTc4NjgxMDU5LCJhdXRob3JpdGllcyI6WyJhZG1pbiIsIlJPTEVfQURNSU4iXSwianRpIjoiY2E4ODZkYjctODgwOS00NmFmLWE2NGUtOWFjNGZmOTU2NTQxIiwiY2xpZW50X2lkIjoiYWRtaW5TZXJ2ZXIifQ.aCYI0lux5I7iwqLdw8ZjMZbeob57SxSVrQInhx5ocKfkDnJbRcyFPpMWLMRtTofN54gxWYcwbt1jygDsXEe9fT02Ma_Y1GNRcyfoaWnSDFPxS0-wushIO_NJO3ZxiyvMPdkbF7XdhVpas8L03WEmdgqIfzsn70wY5eVXDv4hgHP1tWumCtSbFRmDOoypA1ue28ydHRBZYgnKqbG4x8I2esB4BKt8Ux2q3X9x1JNOGC8HqMIpvU_0iZ0FhO6I7cH9TGtpn4iBXIYZl-aWq0nd_EPbIYIKuygE0JDYl04M-MqlxMeaalIouN8pF4NEuAeHIoQsnQ35EvKRrQvVy2bDDQ");
		
		Order order = new Order();
		order.setId(11l);
		order.setProductId(222l);
		
		HttpEntity<Order> entity = new HttpEntity<Order>(order,header);
		
		while (true) {
			try {
				rt.exchange("http://order.security.com:7025/orders/second/1",HttpMethod.GET,entity, String.class);
			} catch (Exception e) {
			}
			Thread.sleep(100);
		}

		
		
	}

}
