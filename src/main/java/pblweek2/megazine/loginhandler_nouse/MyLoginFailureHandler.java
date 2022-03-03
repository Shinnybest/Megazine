package pblweek2.megazine.loginhandler_nouse;

//@Component
//@Getter
//@Setter
//public class MyLoginFailureHandler implements AuthenticationFailureHandler {
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> data = new HashMap<>();
//
//        String errorMsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
//        data.put("result", "fail");
//        data.put("msg", errorMsg);
//
//
//        response.setContentType("text/html; charset=UTF-8"); // 보낼 때 한글 인코딩
//        response.setCharacterEncoding("UTF-8");
//        ServletOutputStream out = response.getOutputStream();
//        out.write(objectMapper.writeValueAsString(data).getBytes("UTF-8"));
//    }
//}