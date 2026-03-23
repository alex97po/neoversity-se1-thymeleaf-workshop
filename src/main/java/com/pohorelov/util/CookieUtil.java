package com.pohorelov.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public class CookieUtil {

  public static final String QUIZ_NICKNAME = "quiz_nickname";

  public static void set(HttpServletResponse resp,
                         String name,
                         String value,
                         int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(maxAge);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    resp.addCookie(cookie);
  }

  public static Optional<String> get(HttpServletRequest req,
                                     String name) {
    final var cookies = req.getCookies();
    if (cookies == null) {
      return Optional.empty();
    }
    return Arrays.stream(cookies)
        .filter(cookie -> name.equals(cookie.getName()))
        .findFirst()
        .map(Cookie::getValue);
  }

}
