package com.connexion.cps.exception;

import com.connexion.cps.common.domain.AjaxResult;
import com.connexion.cps.service.common.I18nService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * global exception
 *
 * 
 */
@ControllerAdvice
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    private I18nService i18nService;

    public GlobalException(I18nService i18nService) {
        this.i18nService = i18nService;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public final AjaxResult handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        AjaxResult error = AjaxResult.error("server side exception", details);
        logger.error("exception catch. ", ex);
        return error;
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public final AjaxResult handleUGException(Exception ex, WebRequest request) {
//        List<String> details = new ArrayList<>();
//        details.add(ex.getLocalizedMessage());
//        AjaxResult error = AjaxResult.error(ex.getMessage(), details);
//        logger.error("Exception catch. ", ex);
//        return error;
//    }

//    @ExceptionHandler(UGNotFoundException.class)
//    @ResponseBody
//    public final ExceptionView handleUGNotFoundException(UGNotFoundException ex, WebRequest request) {
//        List<String> details = new ArrayList<>();
//        details.add(ex.getLocalizedMessage());
//        ExceptionView error = ExceptionView.buildFailure(ex.getMessage(), details);
//        logger.error("UGNotFoundException catch. ", ex);
//        return error;
//    }

    @ExceptionHandler(InvalidParamException.class)
    @ResponseBody
    public final AjaxResult handleUGInvalidParamException(InvalidParamException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        AjaxResult error = AjaxResult.error(ex.getMessage(), details);
        logger.error("UGInvalidParamException catch. ", ex);
        return error;
    }

    /**
     * handler spring mvc result
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public final AjaxResult handleMethodArgumentNotValidException(Exception ex, WebRequest request) {
        BindingResult bindResult = null;
        if (ex instanceof BindException) {
            bindResult = ((BindException) ex).getBindingResult();
        } else if (ex instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) ex).getBindingResult();
        }

        List<String> details = new ArrayList<>();
        for (FieldError fieldError : bindResult.getFieldErrors()) {
            details.add(fieldError.getDefaultMessage());
        }

        logger.debug("param valid failed for request:{}", request.getDescription(true));
        logger.error("MethodArgumentNotValidException or BindException catch. ", ex);
        AjaxResult error = AjaxResult.error(i18nService.getMessage("params.not.valid"), details);
        return error;
    }

    @ExceptionHandler(GosdkException.class)
    @ResponseBody
    public final AjaxResult handleGosdkException(GosdkException ex, WebRequest request) {
        return new AjaxResult(AjaxResult.Type.GO_SDK_ERROR, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public final AjaxResult handleAuthenticationException(AuthenticationException ex) {
        return AjaxResult.noAuth(ex.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseBody
    public final AjaxResult handleInvalidTokenException(InvalidTokenException ex) {
        return AjaxResult.noAuth(ex.getMessage());
    }

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseBody
    public final AjaxResult handleUnknownAccountException(UnknownAccountException ex) {
        return AjaxResult.noAuth(ex.getMessage());
    }

    @ExceptionHandler(GameApiException.class)
    @ResponseBody
    public final AjaxResult handleGameApiException(GameApiException ex) {
        logger.error("GameApiException catch. ", ex);
        return new AjaxResult(AjaxResult.Type.GAME_OPEN_API_ERROR, ex.getMessage());
    }
}