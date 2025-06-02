package com.basketball.refereeapp;

import com.basketball.refereeapp.model.*;
import com.basketball.refereeapp.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class RefereeAppIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    @DisplayName("Отримати всі матчі — позитивний сценарій")
    public void testGetAllMatches() throws Exception {
        mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Створити матч — позитивний сценарій")
    public void testCreateMatch() throws Exception {
        Match match = new Match();
        match.setHomeTeam("Київ-Баскет");
        match.setAwayTeam("Хімік");
        match.setMatchDate(LocalDateTime.now().plusDays(3));
        match.setLeagueLevel(LeagueLevel.ВЮБЛ);

        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(match)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.homeTeam", is("Київ-Баскет")));
    }


    @Test
    @DisplayName("Створити призначення — позитивний сценарій")
    public void testCreateAssignment() throws Exception {
        Match match = new Match();
        match.setHomeTeam("Київ");
        match.setAwayTeam("Одеса");
        match.setMatchDate(LocalDateTime.now().plusDays(1));
        match.setLeagueLevel(LeagueLevel.Перша_ліга);
        match = matchRepository.save(match);

        User user = new User();
        user.setUsername("petro.ivanov+" + System.currentTimeMillis() + "@example.com");
        user.setPassword("password123");
        user.setRole(Role.REFEREE);
        user = userRepository.save(user);

        Assignment assignment = new Assignment();
        assignment.setMatch(match);
        assignment.setReferee(user);
        assignment.setStatus(AssignmentStatus.PENDING);

        mockMvc.perform(post("/api/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignment)))
                .andExpect(status().isCreated()) // <--- зміна тут
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    @DisplayName("Отримати користувачів — позитивний сценарій")
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Отримати матч за неіснуючим ID — негативний сценарій")
    public void testGetMatchNotFound() throws Exception {
        mockMvc.perform(get("/api/matches/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Створити матч з порожньою командою — негативний сценарій")
    public void testCreateInvalidMatch() throws Exception {
        Match match = new Match();
        match.setHomeTeam(""); // невалідне поле
        match.setAwayTeam("Хімік");
        match.setMatchDate(LocalDateTime.now().plusDays(3));
        match.setLeagueLevel(LeagueLevel.ВЮБЛ);

        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(match)))
                .andExpect(status().isBadRequest()); // Очікуємо помилку
    }

    @Test
    @DisplayName("Створити призначення з неіснуючим матчем — негативний сценарій")
    public void testCreateAssignmentWithInvalidMatch() throws Exception {
        User user = new User();
        user.setUsername("fake.referee+" + System.currentTimeMillis() + "@example.com");
        user.setPassword("password123");
        user.setRole(Role.REFEREE);
        user = userRepository.save(user);

        Match fakeMatch = new Match();
        fakeMatch.setId(99999L); // неіснуючий ID

        Assignment assignment = new Assignment();
        assignment.setMatch(fakeMatch);
        assignment.setReferee(user);
        assignment.setStatus(AssignmentStatus.PENDING);

        mockMvc.perform(post("/api/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignment)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Створити призначення з неіснуючим рефері — негативний сценарій")
    public void testCreateAssignmentWithInvalidReferee() throws Exception {
        Match match = new Match();
        match.setHomeTeam("Київ");
        match.setAwayTeam("Одеса");
        match.setMatchDate(LocalDateTime.now().plusDays(1));
        match.setLeagueLevel(LeagueLevel.ВЮБЛ);
        match = matchRepository.save(match);

        User fakeUser = new User();
        fakeUser.setId(99999L); // неіснуючий ID

        Assignment assignment = new Assignment();
        assignment.setMatch(match);
        assignment.setReferee(fakeUser);
        assignment.setStatus(AssignmentStatus.PENDING);

        mockMvc.perform(post("/api/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignment)))
                .andExpect(status().isBadRequest());
    }


}
