package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.ArticleMapper;
import com.commit.collaboration_board_server.mapper.AttendanceMapper;
import com.commit.collaboration_board_server.mapper.UserMapper;
import com.commit.collaboration_board_server.mapper.VacationMapper;
import com.commit.collaboration_board_server.model.Article;
import com.commit.collaboration_board_server.model.Attendance;
import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.model.Vacation;
import com.commit.collaboration_board_server.util.ResponseStatusUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoreFunctionalityTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private AttendanceMapper attendanceMapper;

    @Mock
    private VacationMapper vacationMapper;

    @Mock
    private EmailService emailService;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private ArticleService articleService;

    @InjectMocks
    private AttendanceManagementService attendanceManagementService;

    @InjectMocks
    private VacationService vacationService;

    private User user;
    private Article article;
    private Attendance attendance;
    private Vacation vacation;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserNo(1L);
        user.setUserId("testUser");
        user.setPassword("password123");

        article = new Article();
        article.setId(1);
        article.setTitle("Test Article");
        article.setContent("Content");
        article.setUserNo(1);

        attendance = new Attendance();
        attendance.setUserNo(1L);
        attendance.setStartTime(LocalDateTime.of(2025, 4, 25, 11, 0)); // 코어타임 내

        vacation = new Vacation();
        vacation.setUserNo(1);
        vacation.setStartTime(LocalDateTime.of(2025, 5, 1, 0, 0));
        vacation.setEndTime(LocalDateTime.of(2025, 5, 2, 0, 0));
        vacation.setVacationCategoryId(1);
    }

    @Test
    void testUserAuthentication_Success() {
        when(userMapper.findByUserId(1L)).thenReturn(user);
        when(session.getAttribute("loggedInUser")).thenReturn(null);
        doNothing().when(session).setAttribute(anyString(), any());

        boolean isAuthenticated = userService.authenticate(user);
        userService.saveUserSession(session, user);

        assertTrue(isAuthenticated);
        verify(userMapper, times(2)).findByUserId(1L);
        verify(session).setAttribute("loggedInUser", user);
    }

    @Test
    void testUserLogout() {
        when(request.getSession(false)).thenReturn(session);

        userService.removeUserSession(request);

        verify(session).invalidate();
    }

    @Test
    void testCreateArticle() {
        doNothing().when(articleMapper).insertArticle(any(Article.class));

        articleService.createArticle(article);

        verify(articleMapper).insertArticle(article);
        assertEquals(0, article.getViewCount());
        assertEquals(0, article.getLikeCount());
        assertNotNull(article.getModificateTime());
    }

    @Test
    void testSaveAttendanceOperation_Success() {
        when(attendanceMapper.existsAttendanceByParams(any(Attendance.class))).thenReturn(false);
        when(attendanceMapper.existsUnfinishedAttendance(1L)).thenReturn(false);
        when(userMapper.findByUserId(1L)).thenReturn(user);
        user.setEmail("test@example.com");
        doNothing().when(attendanceMapper).insertAttendance(any(Attendance.class));

        Integer result = attendanceManagementService.saveAttendanceOperation(attendance);

        assertEquals(ResponseStatusUtil.CODES_SUCCESS, result);
        verify(attendanceMapper).existsAttendanceByParams(any(Attendance.class));
        verify(attendanceMapper).existsUnfinishedAttendance(1L);
        verify(attendanceMapper).insertAttendance(attendance);
        verify(userMapper).findByUserId(1L);
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testApplyVacation() {
        when(vacationMapper.insertVacation(any(Vacation.class))).thenReturn(1);

        //vacationService.applyVacation(vacation);

        assertEquals("PENDING", vacation.getStatus());
        verify(vacationMapper).insertVacation(vacation);
    }
}