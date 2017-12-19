package com.lykke.algostoremanager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerservicemanagerApplicationTests {

//	@Value("${local.server.port}")
//	private int localServerPort;

	@Test
	public void contextLoads() {
	}


	@Test
	public void algoGet() {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/algo/get", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	public void algoTestGet() {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/algo/test/get", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}


	@Test
	public void algoServiceGet() {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/algo/service/get", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

}
