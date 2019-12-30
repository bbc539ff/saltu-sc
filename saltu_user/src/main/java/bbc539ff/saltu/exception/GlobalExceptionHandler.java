package bbc539ff.saltu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Result ExceptionHandler(HttpServletRequest req, Exception e) {
    e.printStackTrace();
    logger.error("发生业务异常！原因是：");
    return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
  }
}
