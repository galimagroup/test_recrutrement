
package start.amdev.test.infrastructure.config.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author G2k R&D
 */

@Component
@RequiredArgsConstructor
public class AuthUtils {
    private static final String REQUEST_HEADER_NAME = "Authorization";
    private static final String AUTH_HEADER_BEARER_NAME = "Bearer ";
    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"

    };

    public static String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(REQUEST_HEADER_NAME);

        if (authHeader != null && authHeader.startsWith(AUTH_HEADER_BEARER_NAME)) {
            return authHeader.replace(AUTH_HEADER_BEARER_NAME, "");
        }

        return null;
    }

    public static String getIpAddr(HttpServletRequest request) {
        for (String header : IP_HEADERS) {
            String value = request.getHeader(header);
            if (value == null || value.isEmpty()) {
                continue;
            }
            String[] parts = StringUtils.trimArrayElements(StringUtils.delimitedListToStringArray(value, ","));
            return parts[0];
        }
        return request.getRemoteAddr();
    }

}
