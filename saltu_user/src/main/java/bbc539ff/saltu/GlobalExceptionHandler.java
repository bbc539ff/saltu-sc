package bbc539ff.saltu;

import bbc539ff.saltu.exception.Result;
import bbc539ff.saltu.exception.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = Exception.class)
  public Result ExceptionHandler(HttpServletRequest req, Exception e) {
    e.printStackTrace();
    logger.error("发生业务异常！原因是：{}");
    return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
  }
}
