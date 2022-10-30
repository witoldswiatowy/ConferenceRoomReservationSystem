package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mojeprojecty.conferenceroomreservationsystem.ConferenceRoomReservationSystemApplication;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;


import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ConferenceRoomReservationSystemApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrganizationRepository organizationRepository;

    @Test
    void shouldReturnOrganization_whenExistInDb() throws Exception {
        //given
        OrganizationEntity macroHard = new OrganizationEntity("MacroHard");
        organizationRepository.save(macroHard);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "MacroHard"))
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].name", equalTo("MacroHard")));
    }

    @Test
    void shouldThrownException_whenCreateOrganizationAlreadyExistInDb() throws Exception {
        //given
        String name = "MacroHard";
        OrganizationEntity macroHard = new OrganizationEntity(name);
        organizationRepository.save(macroHard);
        String requestBody = """
                {
                  "name": "%s"
                }
                """.formatted(name);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
        verify(organizationRepository, times(1)).save(any());
    }

}
