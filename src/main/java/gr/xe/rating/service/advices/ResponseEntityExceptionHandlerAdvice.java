package gr.xe.rating.service.advices;


import gr.xe.rating.service.caches.AuditCache;
import gr.xe.rating.service.models.dto.AuditInfoDto;
import gr.xe.rating.service.models.dto.ResponseInfo;
import gr.xe.rating.service.properties.AppProperties;
import gr.xe.rating.service.utils.CommonLib;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * Global Exception Handler for Controllers
 *
 * @author avacondios-xps
 * @since v.0.0.0
 */
@Slf4j
@RestControllerAdvice
public class ResponseEntityExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private final AppProperties appProperties;
    private final AuditCache auditCache;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public ResponseEntityExceptionHandlerAdvice(AppProperties appProperties,AuditCache auditCache, HttpServletRequest httpServletRequest) {
        super();
        if (log.isDebugEnabled()) log.debug("RestControllerAdvice Initialized.");
        this.appProperties = appProperties;
        this.auditCache = auditCache;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(@Nullable HttpMediaTypeNotAcceptableException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleMissingPathVariable(@Nullable MissingPathVariableException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleMissingServletRequestParameter(@Nullable MissingServletRequestParameterException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleServletRequestBindingException(@Nullable ServletRequestBindingException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleConversionNotSupported(@Nullable ConversionNotSupportedException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleTypeMismatch(@Nullable TypeMismatchException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleHttpMessageNotReadable(@Nullable HttpMessageNotReadableException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleHttpMessageNotWritable(@Nullable HttpMessageNotWritableException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleMethodArgumentNotValid(@Nullable MethodArgumentNotValidException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleMissingServletRequestPart(@Nullable MissingServletRequestPartException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleBindException(@Nullable org.springframework.validation.BindException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleNoHandlerFoundException(@Nullable NoHandlerFoundException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported( @NonNull HttpRequestMethodNotSupportedException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable  WebRequest request) {
        pageNotFoundLogger.warn(ex.getMessage());
        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!CollectionUtils.isEmpty(supportedMethods)) {
            headers.setAllow(supportedMethods);
        }
        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(@NonNull HttpMediaTypeNotSupportedException ex,@Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            headers.setAccept(mediaTypes);
            if (request instanceof ServletWebRequest) {
                ServletWebRequest servletWebRequest = (ServletWebRequest)request;
                if (HttpMethod.PATCH.equals(servletWebRequest.getHttpMethod())) {
                    headers.setAcceptPatch(mediaTypes);
                }
            }
        }

        return buildErrorResponseForExceptions(ex, status, request,  headers);
    }

    @Override
    public ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest webRequest) {
        if (webRequest instanceof ServletWebRequest) {
            ServletWebRequest servletWebRequest = (ServletWebRequest)webRequest;
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                AuditInfoDto audit = CommonLib.returnAuditInfo(httpServletRequest, auditCache);

                if (log.isWarnEnabled()) log.warn("[{}] => Async request timed out : [{} - {}], with message: {}",
                        audit.getTransaction_id(), audit.getTransaction_method(), audit.getTransaction_URI(), ex.getMessage());

                return null;
            }
        }

        return buildErrorResponse(ex, ex.getMessage(), status, webRequest, headers);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleExceptionInternal( @NonNull Exception ex, @Nullable Object body, @Nullable HttpHeaders headers, @Nullable HttpStatus status, @Nullable WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        return buildErrorResponse(ex,  ex.getMessage(), status, request, headers);

    }

    // 400
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest request) {
        log.error("Data Integrity Violation", exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.BAD_REQUEST, request, null); //servletLib.getHeaders(request)
    }

    //500
    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
        log.error("Unknown error occurred", exception);
        return buildErrorResponse(exception, "Unknown_error_occurred", HttpStatus.INTERNAL_SERVER_ERROR, request, null); //servletLib.getHeaders(request)
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus, WebRequest request, HttpHeaders headers) {
        ResponseInfo errorResponseInfo = new ResponseInfo(appProperties.getName(), appProperties.getVersion());
        HttpHeaders responseHeaders = headers == null ? new HttpHeaders() : headers;
        AuditInfoDto audit = auditCache.getAudit((String) httpServletRequest.getAttribute("x-server-transaction-id"));

        if (log.isWarnEnabled()) log.warn("[{}] => Error on transaction : [{} - {}], with message: {}",
                audit.getTransaction_id(), audit.getTransaction_method(), audit.getTransaction_URI(), message);

        errorResponseInfo.setErrors(CommonLib.getStackTraceErrors("com.avakio", exception.getStackTrace()));
        errorResponseInfo.setStatus(httpStatus.value());
        errorResponseInfo.setError(exception.toString());
        errorResponseInfo.setMessage(message);
        errorResponseInfo.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(errorResponseInfo, responseHeaders, HttpStatus.valueOf(errorResponseInfo.getStatus()));
    }

    private ResponseEntity<Object> buildErrorResponseForExceptions(Exception exception, HttpStatus httpStatus, WebRequest request, HttpHeaders headers) {
        ResponseInfo errorResponseInfo = new ResponseInfo(appProperties.getName(), appProperties.getVersion());
        HttpHeaders responseHeaders = headers == null ? new HttpHeaders() : headers;
        AuditInfoDto audit = auditCache.getAudit((String) httpServletRequest.getAttribute("x-server-transaction-id"));

        if (log.isWarnEnabled()) log.warn("[{}] => Error on transaction : [{} - {}], with message: {}",
                audit.getTransaction_id(), audit.getTransaction_method(), audit.getTransaction_URI(), exception.getMessage());

        errorResponseInfo.setStatus(httpStatus.value());
        errorResponseInfo.setError(exception.getMessage());
        errorResponseInfo.setMessage(CommonLib.getClassName(exception.getClass().getCanonicalName()));
        errorResponseInfo.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(errorResponseInfo, responseHeaders, HttpStatus.valueOf(errorResponseInfo.getStatus()));
    }

}

