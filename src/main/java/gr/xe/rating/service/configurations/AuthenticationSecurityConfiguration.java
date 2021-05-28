package gr.xe.rating.service.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.xe.rating.service.caches.AuditCache;
import gr.xe.rating.service.models.dto.AuditInfoDto;
import gr.xe.rating.service.models.dto.ResponseInfo;
import gr.xe.rating.service.properties.AppProperties;
import gr.xe.rating.service.utils.CommonLib;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Configuration for the Basic Auth.
 */
@Component
@Slf4j
public class AuthenticationSecurityConfiguration implements AuthenticationEntryPoint {

    private final AppProperties appProperties;
    private final AuditCache auditCache;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthenticationSecurityConfiguration(AppProperties appProperties,
                                               AuditCache auditCache,
                                               ObjectMapper objectMapper) {
        this.appProperties = appProperties;
        this.auditCache = auditCache;
        this.objectMapper = objectMapper;
        if (log.isDebugEnabled()) log.debug("Component Initialized.");
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException exception) throws IOException {

        ResponseInfo errorResponseInfo = new ResponseInfo(appProperties.getName(), appProperties.getVersion());
        AuditInfoDto audit = CommonLib.returnAuditInfo(httpServletRequest, auditCache);

        if (log.isWarnEnabled()) log.warn("[{}] => Error on Authentication/Authorization : [{} - {}], with message: {}",
                audit.getTransaction_id(), audit.getTransaction_method(), audit.getTransaction_URI(), exception.getMessage());

        String jsonString = CommonLib.bufferReaderToString(httpServletRequest.getReader());
        Map<String, Object> requestMap = CommonLib.jsonStringToMap(jsonString);
        audit.setRequestBody(requestMap);

        errorResponseInfo.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponseInfo.setError(exception.getMessage());
        errorResponseInfo.setMessage(CommonLib.getClassName(exception.getClass().getCanonicalName()));
        errorResponseInfo.setPath(audit.getTransaction_URI());

        Object auditBody = objectMapper.convertValue(errorResponseInfo, Map.class);
        audit.setResponseBody(auditBody);
        auditCache.updateAudit(audit);

        String retStr = objectMapper.writeValueAsString(errorResponseInfo);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setContentLength(retStr.length() + 2);
        httpServletResponse.getOutputStream().println(retStr);
    }
}
