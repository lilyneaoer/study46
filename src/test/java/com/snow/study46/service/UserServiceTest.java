package com.snow.study46.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.snow.study46.entity.User;
import com.snow.study46.model.dto.ModifyPasswordDTO;
import com.snow.study46.model.dto.RegisterDTO;
import com.snow.study46.model.dto.UserIdDTO;
import com.snow.study46.model.vo.BaseVo;
import com.snow.study46.model.vo.UserVo;
import com.snow.study46.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  /**
   * BaseVo 构造方法内部调用 ServletUriComponentsBuilder.fromCurrentRequest()，
   * 纯单元测试中不存在 Servlet 请求上下文，会抛出 IllegalStateException，
   * 因此这里通过 RequestContextHolder 注入一个 MockHttpServletRequest。
   */
  @BeforeEach
  void setUpRequestContext() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("/user/test");
    request.setServerName("localhost");
    request.setServerPort(8080);
    ServletRequestAttributes attrs = new ServletRequestAttributes(request);
    RequestContextHolder.setRequestAttributes(attrs);
  }

  @AfterEach
  void clearRequestContext() {
    RequestContextHolder.resetRequestAttributes();
  }

  // ==================== login 登录 ====================

  @Test
  @DisplayName("登录成功")
  void login_success() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("snow");
    form.setPassword("123456");

    User user = new User();
    user.setId("1");
    user.setUsername("snow");
    user.setPassword("123456");

    when(userRepository.searchUserByUsername("snow")).thenReturn(Optional.of(user));

    BaseVo<Optional<UserVo>> result = userService.login(form);

    assertTrue(result.isSuccess());
    assertEquals("登录成功", result.getMsg());
    assertTrue(result.getData().isPresent());
    assertEquals("1", result.getData().get().getId());
    assertEquals("snow", result.getData().get().getUsername());
    verify(userRepository, times(1)).searchUserByUsername("snow");
  }

  @Test
  @DisplayName("登录失败-用户不存在")
  void login_userNotFound() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("unknown");
    form.setPassword("123456");

    when(userRepository.searchUserByUsername("unknown")).thenReturn(Optional.empty());

    BaseVo<Optional<UserVo>> result = userService.login(form);

    assertFalse(result.isSuccess());
    assertEquals("登录失败, 用户不存在", result.getMsg());
    verify(userRepository, never()).getUserById(anyString());
  }

  @Test
  @DisplayName("登录失败-密码错误")
  void login_wrongPassword() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("snow");
    form.setPassword("wrongpwd");

    User user = new User();
    user.setId("1");
    user.setUsername("snow");
    user.setPassword("123456");

    when(userRepository.searchUserByUsername("snow")).thenReturn(Optional.of(user));

    BaseVo<Optional<UserVo>> result = userService.login(form);

    assertFalse(result.isSuccess());
    assertEquals("登录失败, 密码错误", result.getMsg());
  }

  // ==================== registerUser 注册 ====================

  @Test
  @DisplayName("注册成功")
  void register_success() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("newuser");
    form.setPassword("pwd");

    when(userRepository.searchUserByUsername("newuser")).thenReturn(Optional.empty());
    when(userRepository.insertUser(form)).thenReturn(1);

    BaseVo<Object> result = userService.registerUser(form);

    assertTrue(result.isSuccess());
    assertEquals("注册成功", result.getMsg());
    verify(userRepository, times(1)).insertUser(form);
  }

  @Test
  @DisplayName("注册失败-用户已存在")
  void register_userAlreadyExists() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("snow");
    form.setPassword("pwd");

    User existing = new User();
    existing.setUsername("snow");
    when(userRepository.searchUserByUsername("snow")).thenReturn(Optional.of(existing));

    BaseVo<Object> result = userService.registerUser(form);

    assertFalse(result.isSuccess());
    assertEquals("用户已存在", result.getMsg());
    verify(userRepository, never()).insertUser(any(RegisterDTO.class));
  }

  @Test
  @DisplayName("注册失败-插入数据库失败")
  void register_insertFailed() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("newuser");
    form.setPassword("pwd");

    when(userRepository.searchUserByUsername("newuser")).thenReturn(Optional.empty());
    when(userRepository.insertUser(form)).thenReturn(0);

    BaseVo<Object> result = userService.registerUser(form);

    assertFalse(result.isSuccess());
    assertEquals("注册失败", result.getMsg());
  }

  // ==================== modifyUserPassword 修改密码 ====================

  @Test
  @DisplayName("修改密码成功")
  void modifyPassword_success() {
    ModifyPasswordDTO form = new ModifyPasswordDTO();
    form.setId("1");
    form.setOldPassword("oldpwd");
    form.setNewPassword("newpwd");

    User user = new User();
    user.setId("1");
    user.setPassword("oldpwd");

    when(userRepository.getUserById("1")).thenReturn(Optional.of(user));
    when(userRepository.updateUserPassword("1", "newpwd")).thenReturn(1);

    BaseVo<Object> result = userService.modifyUserPassword(form);

    assertTrue(result.isSuccess());
    assertEquals("修改成功", result.getMsg());
    verify(userRepository, times(1)).updateUserPassword("1", "newpwd");
  }

  @Test
  @DisplayName("修改密码失败-用户不存在")
  void modifyPassword_userNotFound() {
    ModifyPasswordDTO form = new ModifyPasswordDTO();
    form.setId("99");
    form.setOldPassword("oldpwd");
    form.setNewPassword("newpwd");

    when(userRepository.getUserById("99")).thenReturn(Optional.empty());

    BaseVo<Object> result = userService.modifyUserPassword(form);

    assertFalse(result.isSuccess());
    assertEquals("用户不存在", result.getMsg());
    verify(userRepository, never()).updateUserPassword(anyString(), anyString());
  }

  @Test
  @DisplayName("修改密码失败-旧密码错误")
  void modifyPassword_wrongOldPassword() {
    ModifyPasswordDTO form = new ModifyPasswordDTO();
    form.setId("1");
    form.setOldPassword("wrongold");
    form.setNewPassword("newpwd");

    User user = new User();
    user.setId("1");
    user.setPassword("oldpwd");

    when(userRepository.getUserById("1")).thenReturn(Optional.of(user));

    BaseVo<Object> result = userService.modifyUserPassword(form);

    assertFalse(result.isSuccess());
    assertEquals("密码输入错误", result.getMsg());
    verify(userRepository, never()).updateUserPassword(anyString(), anyString());
  }

  @Test
  @DisplayName("修改密码失败-更新数据库失败")
  void modifyPassword_updateFailed() {
    ModifyPasswordDTO form = new ModifyPasswordDTO();
    form.setId("1");
    form.setOldPassword("oldpwd");
    form.setNewPassword("newpwd");

    User user = new User();
    user.setId("1");
    user.setPassword("oldpwd");

    when(userRepository.getUserById("1")).thenReturn(Optional.of(user));
    when(userRepository.updateUserPassword("1", "newpwd")).thenReturn(0);

    BaseVo<Object> result = userService.modifyUserPassword(form);

    assertFalse(result.isSuccess());
    assertEquals("修改失败", result.getMsg());
  }

  // ==================== removeUser 删除用户（用户名+密码） ====================

  @Test
  @DisplayName("删除用户成功")
  void removeUser_success() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("snow");
    form.setPassword("123456");

    User user = new User();
    user.setId("1");
    user.setUsername("snow");
    user.setPassword("123456");

    when(userRepository.searchUserByUsername("snow")).thenReturn(Optional.of(user));
    when(userRepository.removeUserById("1")).thenReturn(1);

    BaseVo<Object> result = userService.removeUser(form);

    assertTrue(result.isSuccess());
    assertEquals("用户snow删除成功", result.getMsg());
    verify(userRepository, times(1)).removeUserById("1");
  }

  @Test
  @DisplayName("删除用户失败-用户不存在")
  void removeUser_userNotFound() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("unknown");
    form.setPassword("123456");

    when(userRepository.searchUserByUsername("unknown")).thenReturn(Optional.empty());

    BaseVo<Object> result = userService.removeUser(form);

    assertFalse(result.isSuccess());
    assertEquals("删除失败, 用户不存在", result.getMsg());
    verify(userRepository, never()).removeUserById(anyString());
  }

  @Test
  @DisplayName("删除用户失败-密码错误")
  void removeUser_wrongPassword() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("snow");
    form.setPassword("wrongpwd");

    User user = new User();
    user.setId("1");
    user.setUsername("snow");
    user.setPassword("123456");

    when(userRepository.searchUserByUsername("snow")).thenReturn(Optional.of(user));

    BaseVo<Object> result = userService.removeUser(form);

    assertFalse(result.isSuccess());
    assertEquals("删除失败, 密码错误", result.getMsg());
    verify(userRepository, never()).removeUserById(anyString());
  }

  @Test
  @DisplayName("删除用户失败-数据库删除失败")
  void removeUser_deleteFailed() {
    RegisterDTO form = new RegisterDTO();
    form.setUsername("snow");
    form.setPassword("123456");

    User user = new User();
    user.setId("1");
    user.setUsername("snow");
    user.setPassword("123456");

    when(userRepository.searchUserByUsername("snow")).thenReturn(Optional.of(user));
    when(userRepository.removeUserById("1")).thenReturn(0);

    BaseVo<Object> result = userService.removeUser(form);

    assertFalse(result.isSuccess());
    assertEquals("删除失败", result.getMsg());
  }

  // ==================== removeUserById 按ID删除用户 ====================

  @Test
  @DisplayName("按ID删除用户成功")
  void removeUserById_success() {
    UserIdDTO idDto = new UserIdDTO();
    idDto.setId("1");

    User user = new User();
    user.setId("1");
    user.setUsername("snow");

    when(userRepository.getUserById("1")).thenReturn(Optional.of(user));
    when(userRepository.removeUserById("1")).thenReturn(1);

    BaseVo<Object> result = userService.removeUserById(idDto);

    assertTrue(result.isSuccess());
    assertEquals("用户snow删除成功", result.getMsg());
    verify(userRepository, times(1)).removeUserById("1");
  }

  @Test
  @DisplayName("按ID删除用户失败-用户不存在")
  void removeUserById_userNotFound() {
    UserIdDTO idDto = new UserIdDTO();
    idDto.setId("99");

    when(userRepository.getUserById("99")).thenReturn(Optional.empty());

    BaseVo<Object> result = userService.removeUserById(idDto);

    assertFalse(result.isSuccess());
    assertEquals("删除失败, id为99的用户不存在", result.getMsg());
    verify(userRepository, never()).removeUserById(anyString());
  }

  @Test
  @DisplayName("按ID删除用户失败-数据库删除失败")
  void removeUserById_deleteFailed() {
    UserIdDTO idDto = new UserIdDTO();
    idDto.setId("1");

    User user = new User();
    user.setId("1");
    user.setUsername("snow");

    when(userRepository.getUserById("1")).thenReturn(Optional.of(user));
    when(userRepository.removeUserById("1")).thenReturn(0);

    BaseVo<Object> result = userService.removeUserById(idDto);

    assertFalse(result.isSuccess());
    assertEquals("删除失败", result.getMsg());
  }
}
