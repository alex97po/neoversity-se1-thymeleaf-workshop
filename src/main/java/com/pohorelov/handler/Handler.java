package com.pohorelov.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Handler {

  void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}
