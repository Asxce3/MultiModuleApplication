package org.example.hub.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.example.hub.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Component
public class HubUtils {
    @Autowired
    private AppConfig appConfig;

    public String getUrl(HttpServletRequest request){
        try {
            URL url = new URL(request.getRequestURL().toString());

            String path = url.getPath().substring(1).split("/")[0];

            request.setAttribute(RedirectView.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
            return appConfig.getHost() + appConfig.getPorts().get(path) + request.getRequestURI();


        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelAndView setAttributes(UUID id, RedirectAttributes redirectAttributes, String url) {
        redirectAttributes.addAttribute("id", id);
        return new ModelAndView("redirect:" + url);
    }
}
