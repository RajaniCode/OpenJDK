/*
 * Copyright 2013-2104 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package security;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import bookmarks.Application;

/**
 * @author Dave Syer
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port=0")
@WebAppConfiguration
public class ApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	public void passwordGrant() {
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("username", "jlong");
		request.set("password", "password");
		request.set("grant_type", "password");
		
		System.out.println("request###############################################################################################");
		System.out.println(request);
		System.out.println("request###############################################################################################");
                
		@SuppressWarnings("unchecked")
		Map<String, Object> token = new TestRestTemplate("android-bookmarks", "123456")
				.postForObject("http://localhost:" + port + "/oauth/token", request,
						Map.class);

	        System.out.println("Post###############################################################################################");
		System.out.println("http://localhost:" + port + "/oauth/token");
		System.out.println("Post###############################################################################################");

		System.out.println("token###############################################################################################");
		System.out.println(token);
		System.out.println("token###############################################################################################");
				
		assertNotNull("Wrong response: " + token, token.get("access_token"));
	}

}
