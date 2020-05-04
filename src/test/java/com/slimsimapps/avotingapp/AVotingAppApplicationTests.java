package com.slimsimapps.avotingapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AVotingAppApplicationTests {

	@Test
	void contextLoads() {
		assertThat(6).isNotNull();
	}

}
