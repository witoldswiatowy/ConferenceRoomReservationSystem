package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.mojeprojecty.conferenceroomreservationsystem.ConferenceRoomReservationSystemApplication;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomEntity;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.OrganizationRepository;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ConferenceRoomReservationSystemApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class ConferenceRoomControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    @Test
    void shouldReturnListOfExistConferenceRoom() throws Exception {
        //given
        OrganizationEntity google = organizationRepository.save(new OrganizationEntity("Google"));
        ConferenceRoomEntity barbados = new ConferenceRoomEntity("Barbados", "Barbados", 1, true, 10, 2, google);
        ConferenceRoomEntity sumatra = new ConferenceRoomEntity("Sumatra", "Sumatra", 1, true, 10, 2, google);

//        conferenceRoomRepository.save(barbados);
//        conferenceRoomRepository.save(sumatra);

        conferenceRoomRepository.saveAll(Arrays.asList(barbados, sumatra));

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/conferenceRooms?sortType=DESC").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", equalTo("Sumatra")))
                .andExpect(jsonPath("$[1].name", equalTo("Barbados")));
    }

    @Test
    void shouldCreateConferenceRoom_whenRequestIsCorrect() throws Exception {
        //given
        OrganizationEntity google = organizationRepository.save(new OrganizationEntity("Google"));

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/conferenceRooms").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"availability\": true,\n" +
                        "  \"floor\": 1,\n" +
                        "  \"identifier\": \"Java\",\n" +
                        "  \"name\": \"Java\",\n" +
                        "  \"numberOfHammock\": 0,\n" +
                        "  \"numberOfSeats\": 0,\n" +
                        "  \"organizationId\": " + google.getId() + "\n" +
                        "}"))
                .andExpect(jsonPath("$.name", equalTo("Java")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.floor", equalTo(1)));
    }

    @Test
    void shouldUpdateConferenceRoom_whenRequestIsCorrect() throws Exception {
        //given
        OrganizationEntity google = organizationRepository.save(new OrganizationEntity("Google"));
        ConferenceRoomEntity barbados = conferenceRoomRepository.save(new ConferenceRoomEntity("Barbados", "Barbados", 1, true, 10, 2, google));

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/conferenceRooms").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"conferenceRoomId\": 2,\n" +
                        "  \"name\": \"Zmieniona\",\n" +
                        "  \"organizationId\": " + google.getId() + "\n" +
                        "}"))
                .andExpect(jsonPath("$.name", equalTo("Zmieniona")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.floor", equalTo(1)));
    }

    @AfterEach
    public void tearDown() {
        conferenceRoomRepository.deleteAll();
        organizationRepository.deleteAll();
    }

}