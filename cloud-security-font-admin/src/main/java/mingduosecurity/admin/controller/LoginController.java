package mingduosecurity.admin.controller;

import lombok.extern.slf4j.Slf4j;
import mingduosecurity.admin.domain.TokenInfo;
import mingduosecurity.admin.properties.AdminProperties;
import mingduosecurity.admin.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/20
 */
@Slf4j
@RestController
public class LoginController {


    @Autowired
    AdminProperties adminProperties;
    /**
     * 调用 认证服务器 /token
     */
    @PostMapping("/loginCallback")
    public void loginCallback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {

        TokenInfo tokenInfo = TokenUtils.getTokenInfo(code);
        log.info("result = [" + tokenInfo + "]");

        adminProperties.getStoreStrategy().process(new ServletWebRequest(request,response),tokenInfo );
    }




    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
