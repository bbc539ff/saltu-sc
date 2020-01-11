package bbc539ff.saltu.post.exception;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setCharacterEncoding("utf-8");
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    ObjectMapper objectMapper = new ObjectMapper();
    String resBody =
        objectMapper.writeValueAsString(Result.failure(ResultCode.PERMISSION_NO_ACCESS, "SimpleAccessDeniedHandler"));
    PrintWriter printWriter = response.getWriter();
    printWriter.print(resBody);
    printWriter.flush();
    printWriter.close();
  }
}
