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
		header.add("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIiwiZ2V0ZXdheSJdLCJ1c2VyX25hbWUiOiIxMjMxMjMiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTc5MTAwMDA1LCJhdXRob3JpdGllcyI6WyJhZG1pbiIsIlJPTEVfQURNSU4iXSwianRpIjoiZDczNjIzNWEtNzI3OS00NzM3LWJhYzktMjJiYjMzMjU2ZTMxIiwiY2xpZW50X2lkIjoiYWRtaW5TZXJ2ZXIifQ.CNwmKGi-otdfPcZ5KWuvIwq5wt6mBGOamdChc6NIQXIRmuOi6D9X0pdxKGuR-K4NgPe0IBhGn-oiUW2opK4O_39Tdvk67m5xIA0qJ995e2IfHEmS1YqNbqfr12_5ljQJ2jkDzBow0Z6e2OmR2XcdmZAIz1uDFA6u-jE2vivD0ilRsejAo0n52d0o-SHLGiD1fu0XTVhe_KtE1C9kJOVlAzmW3ASoNSP0K__GPsKVdiC5QBqxVoUU7j6CFeamS-56su8UF64dwtasoGLPtds8txtlJxns53vNdVHfuMX6C5SED2QHPL19_IiTwGIMdVwTE7s14J4B6963gpW0T2c4HA");
		
		Order order = new Order();
		order.setId(11l);
		order.setProductId(222l);
		
		HttpEntity<Order> entity = new HttpEntity<Order>(order,header);
		
		while (true) {
			try {
				rt.exchange("http://order.security.com:7025/orders",HttpMethod.POST,entity, String.class);
			} catch (Exception e) {
			}
			Thread.sleep(100);
		}

		
		
	}

}
