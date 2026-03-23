package com.pohorelov.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

public class ThymeleafConfig {

  private static TemplateEngine templateEngine;

  public static void init() {
    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
    resolver.setPrefix("templates/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode(TemplateMode.HTML);
    resolver.setCharacterEncoding("UTF-8");
    resolver.setCacheable(false);
    templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(resolver);
  }

  public static void render(String templateName,
                            Map<String, Object> model,
                            HttpServletRequest req,
                            HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html;charset=UTF-8");
    final var servletContext = req.getServletContext();
    final var jakartaServletWebApplication =
        JakartaServletWebApplication.buildApplication(servletContext);

    final var webContext =
        new WebContext(
            jakartaServletWebApplication.buildExchange(req, resp),
            req.getLocale());
    webContext.setVariables(model);

    templateEngine.process(templateName, webContext, resp.getWriter());
  }

}
