package com.pohorelov.handler;

import static com.pohorelov.util.CookieUtil.QUIZ_NICKNAME;

import com.pohorelov.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileHandler implements Handler {

  @Override
  public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String nickname = req.getParameter("nickname");
    if (nickname != null && !nickname.isBlank()) {
      CookieUtil.set(resp, QUIZ_NICKNAME, nickname, 30 * 24 * 60 * 60);
    }
    resp.sendRedirect(req.getContextPath() + "/");
  }
}
