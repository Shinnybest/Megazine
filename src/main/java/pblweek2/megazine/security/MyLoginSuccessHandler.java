package pblweek2.megazine.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import pblweek2.megazine.dto.UserDataResponseDto;
import pblweek2.megazine.entityResponse.LoginSuccess;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

//    private RequestCache requestCache = new HttpSessionRequestCache();
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//    private final String DEFAULT_LOGIN_SUCCESS_URL = "/";

    @Override
    public void onAuthenticationSuccess(javax.servlet.http.HttpServletRequest request,
                                                                javax.servlet.http.HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;

        HttpSession session = request.getSession();

//        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();

        Long userId = userDetails.getUser().getId();
        String username = userDetails.getUser().getUsername();
        String email = userDetails.getUser().getEmail();
//        String session_id = web.getSessionId();



        UserDataResponseDto userDataResponseDto = new UserDataResponseDto(userId, username, email);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();

        String errormsg = "로그인 성공!";

        data.put("result", "success");
        data.put("msg", errormsg);
        data.put("userData", userDataResponseDto);
        data.put("session_id", session.getId());
//        System.out.println(session_id);



        response.setContentType("text/html; charset=UTF-8"); // 보낼 때 한글 인코딩
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.write(objectMapper.writeValueAsString(data).getBytes("UTF-8"));

//        loginSuccess(userDataResponseDto);
    }

//    public ResponseEntity<LoginSuccess> loginSuccess(UserDataResponseDto userDataResponseDto) {
//        return new ResponseEntity<>(new LoginSuccess(userDataResponseDto), HttpStatus.OK);
//    }

}
