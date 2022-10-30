package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrganizationController.class)
class OrganizationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrganizationService organizationService;

    @Test
    void shouldReturnAllOrganization_whenExistAndNotSortParam() throws Exception {
        //given
        OrganizationDto organizationDto = new OrganizationDto(1L, LocalDateTime.now(), LocalDateTime.now(), "MacroHard");
        when(organizationService.getListOfOrganization(null)).thenReturn(List.of(organizationDto));
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].name", equalTo("MacroHard")));
    }

    @Test
    void shouldReturnAllOrganization_whenExistAndAscParam() throws Exception {
        //given
        OrganizationDto organizationDtoFirst = new OrganizationDto(1L, LocalDateTime.now(), LocalDateTime.now(), "MacroHard");
        OrganizationDto organizationDtoSecond = new OrganizationDto(2L, LocalDateTime.now(), LocalDateTime.now(), "Banana");
        String sortType = "ASC";
        when(organizationService.getListOfOrganization(SortType.ASC)).thenReturn(List.of(organizationDtoSecond, organizationDtoFirst));
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations")
                        .param("sortType", sortType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", equalTo(2)))
                .andExpect(jsonPath("$.[0].name", equalTo("Banana")))
                .andExpect(jsonPath("$.[1].id", equalTo(1)))
                .andExpect(jsonPath("$.[1].name", equalTo("MacroHard")));
    }


    @Test
    void shouldReturnAllOrganization_whenExistAndDescParam() throws Exception {
        //given
        OrganizationDto organizationDtoFirst = new OrganizationDto(1L, LocalDateTime.now(), LocalDateTime.now(), "MacroHard");
        OrganizationDto organizationDtoSecond = new OrganizationDto(2L, LocalDateTime.now(), LocalDateTime.now(), "Banana");
        String sortType = "DESC";
        when(organizationService.getListOfOrganization(SortType.DESC)).thenReturn(List.of(organizationDtoFirst, organizationDtoSecond));
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations")
                        .param("sortType", sortType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].name", equalTo("MacroHard")))
                .andExpect(jsonPath("$.[1].id", equalTo(2)))
                .andExpect(jsonPath("$.[1].name", equalTo("Banana")));
    }

    @Test
    void shouldCreateOrganization_whenRequestIsCorrect() throws Exception {
        //given
        String name = "TworzoKsiążka";
        OrganizationRequest request = new OrganizationRequest(name);
        OrganizationDto organizationDto = new OrganizationDto(1L, LocalDateTime.now(), LocalDateTime.now(), name);
        when(organizationService.createOrganization(request)).thenReturn(organizationDto);
        String requestBody = """
                {
                  "name": "%s"
                }
                """.formatted(name);
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("TworzoKsiążka")));
    }

    @Test
    void shouldReturnBadRequest_whenNameIsAlreadyExist() throws Exception {
        //given
        String name = "TworzoKsiążka";
        OrganizationRequest request = new OrganizationRequest(name);
        when(organizationService.createOrganization(request)).thenThrow(new IllegalArgumentException("Organization already exists!"));
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
    }

    @Test
    void shouldReturnBadRequest_whenNameIsTooShort() throws Exception {
        //given
        String name = "T";
        String requestBody = """
                {
                  "name": "%s"
                }
                """.formatted(name);
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", equalTo("size must be between 2 and 20")));
    }

    @Test
    void shouldReturnBadRequest_whenNameIsTooLong() throws Exception {
        //given
        String name = "T".repeat(21);
        String requestBody = """
                {
                  "name": "%s"
                }
                """.formatted(name);
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", equalTo("size must be between 2 and 20")));
    }



    @Test
    void shouldDeleteOrganization_whenAlreadyExist() throws Exception {
        //given
        Long organizationId = 1L;
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organizations/{organizationId}", organizationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void shouldThrownException_whenDeleteNotExistingElement() throws Exception {
        //given
        Long organizationId = 0L;
        Mockito.when(organizationService.deleteOrganization(organizationId)).thenThrow(new NoSuchElementException("Can't find organization"));

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organizations/{organizationId}", organizationId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", equalTo("Can't find organization")))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldUpdateOrganization_whenNameIsCorrect() throws Exception {
        //given
        OrganizationDto amazon = new OrganizationDto(1L, LocalDateTime.now(), LocalDateTime.now(), "Amazon");
        Mockito.when(organizationService.updateOrganization(Mockito.any())).thenReturn(amazon);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Google\"\n" +
                                "}")
                ).andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Amazon")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void shouldThrownException_when() throws Exception {
        //given
        Mockito.when(organizationService.updateOrganization(Mockito.any())).thenThrow(new NoSuchElementException("Can't find element to update"));

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Google\"\n" +
                                "}")
                ).andExpect(MockMvcResultMatchers.jsonPath("$", equalTo("Can't find element to update")))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}