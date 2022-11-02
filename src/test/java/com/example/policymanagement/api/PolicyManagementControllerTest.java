package com.example.policymanagement.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.policymanagement.domain.repository.PolicyRepository;
import java.time.LocalDate;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PolicyManagementControllerTest {

  @Autowired
  private PolicyRepository repository;

  @Autowired
  private MockMvc mockMvc;
  ;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  @SneakyThrows
  void testCreation_success() {

    assertThat(repository.findAll().size(), CoreMatchers.is(0));
    this.mockMvc.perform(post("/policy")
            .contentType(APPLICATION_JSON)
            .content(
                """
                        {
                           "startDate":"%s",
                           "insuredPersons":[
                              {
                                 "firstName":"Jane",
                                 "secondName":"Johnson",
                                 "premium":12.90
                              },
                              {
                                 "firstName":"Jack",
                                 "secondName":"Doe",
                                 "premium":15.90
                              }
                           ]
                        }
                    """.formatted(LocalDate.now().plusDays(1).toString())
            ))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.policyId").value("CU423DF89"));
    assertThat(repository.findAll().size(), CoreMatchers.is(1));
  }

  @Test
  @SneakyThrows
  void testCreation_requiredParamMissing_400() {

    assertThat(repository.findAll().size(), CoreMatchers.is(0));
    this.mockMvc.perform(post("/policy")
            .contentType(APPLICATION_JSON)
            .content(
                """
                        {
                           "insuredPersons":[
                              {
                                 "firstName":"Jane",
                                 "secondName":"Johnson",
                                 "premium":12.90
                              },
                              {
                                 "firstName":"Jack",
                                 "secondName":"Doe",
                                 "premium":15.90
                              }
                           ]
                        }
                    """
            ))
        .andDo(print())
        .andExpect(status().is4xxClientError());
    assertThat(repository.findAll().size(), CoreMatchers.is(0));
  }

  @Test
  @SneakyThrows
  void testCreation_requiredNestedParamMissing_400() {

    assertThat(repository.findAll().size(), CoreMatchers.is(0));
    this.mockMvc.perform(post("/policy")
            .contentType(APPLICATION_JSON)
            .content(
                """
                        {
                           "startDate":"%s",
                           "insuredPersons":[
                              {
                                 "firstName":"Jane",                         
                                 "premium":12.90
                              },
                              {
                                 "firstName":"Jack",
                                 "secondName":"Doe",
                                 "premium":15.90
                              }
                           ]
                        }
                    """.formatted(LocalDate.now().plusDays(1).toString())
            ))
        .andDo(print())
        .andExpect(status().is4xxClientError());
    assertThat(repository.findAll().size(), CoreMatchers.is(0));
  }
}