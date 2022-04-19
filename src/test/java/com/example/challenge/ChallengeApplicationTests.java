package com.example.challenge;

import com.example.challenge.counter.IdCountHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"scheduling.cron = -", "server.port = 8080"})
@AutoConfigureMockMvc
class ChallengeApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	@Mock
	private IdCountHandler idCountHandler;

	// dummy test
	@Test
	void contextLoads() {
	}

	// tests the counter
	@Test
	void countTest() throws Exception {

		for (int i = 0; i < 100; i++) {
			mockMvc.perform(get("/api/smaato/accept?id=" + i))
					.andExpect(status().isOk())
					.andExpect(content().string("ok"));
		}

		assertThat(idCountHandler.get()).isEqualTo(100);
	}

	// tests the reset feature
	@Test
	void saveTaskTest() throws Exception {

		for (int i = 0; i < 100; i++) {
			mockMvc.perform(get("/api/smaato/accept?id=" + i))
					.andExpect(status().isOk())
					.andExpect(content().string("ok"));
		}

		idCountHandler.localReset();

		for (int i = 0; i < 50; i++) {
			mockMvc.perform(get("/api/smaato/accept?id=" + i))
					.andExpect(status().isOk())
					.andExpect(content().string("ok"));
		}

		assertThat(idCountHandler.get()).isEqualTo(50);
	}
}
