package com.SpringSecurityPart1.SpringSecurityPart1;

import com.SpringSecurityPart1.SpringSecurityPart1.entities.User;
import com.SpringSecurityPart1.SpringSecurityPart1.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityPart1ApplicationTests {
    @Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
//		User user=new User(4L,"roshan@gmail.com","pass");
//		String token= jwtService.generateToken(user);
//		System.out.println(token);
//		Long id=jwtService.getUserIdFromToken(token);
//		System.out.println(id);



	}

}
