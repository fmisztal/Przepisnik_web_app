package pw.paint.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    @ResponseBody
    String error(HttpServletRequest request) {
        return "<h1>Wystąpił niespodziewany błąd.</h1><br /><h3> Bardzo nam przykro ( ͡° ʖ̯ ͡°)";
    }
}
