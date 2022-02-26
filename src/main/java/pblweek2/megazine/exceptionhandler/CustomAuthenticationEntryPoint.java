package pblweek2.megazine.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        // status를 401 에러로 지정
        // Status code (401) indicating that the request requires HTTP authentication.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // json 리턴 및 한글깨짐 수정.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // "application/json;charset=utf-8"

        JSONObject json = new JSONObject();
        String message = "No Authentication";
        json.put("result", "fail");
        json.put("message", "페이지 권한이 없습니다. 로그인을 해주세요.(Entrypoint)");

        PrintWriter out = response.getWriter();
        out.print(json);


//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> data = new HashMap<>();
//
//        String errormsg = "접근 권한이 없습니다. 로그인 여부를 확인하여 주세요.";
//
//        data.put("result", "fail");
//        data.put("msg", errormsg);
//
//
//        response.setContentType("text/html; charset=UTF-8"); // 보낼 때 한글 인코딩
//        response.setCharacterEncoding("UTF-8");
//        ServletOutputStream out = response.getOutputStream();
//        out.write(objectMapper.writeValueAsString(data).getBytes("UTF-8"));
    }
}
