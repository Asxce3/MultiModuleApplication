package org.example.hub.controllers;


import jakarta.servlet.http.HttpServletRequest;

import org.example.hub.utils.HubUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@RestController
@RequestMapping("/**")
public class HubController {

    @Autowired
    private HubUtils hubUtils;

    @GetMapping()
    public ModelAndView getMany(HttpServletRequest request) {

        try {
            return new ModelAndView("redirect:" + hubUtils.getUrl(request));

        }   catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("*/{id}")
    public ModelAndView getOne(HttpServletRequest request,
                               @PathVariable UUID id,
                               RedirectAttributes redirectAttributes) {

        try {

            String url = hubUtils.getUrl(request);
            return hubUtils.setAttributes(id, redirectAttributes, url);

        }   catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping()
    public ModelAndView create(HttpServletRequest request) {
        try {

            return new ModelAndView("redirect:" + hubUtils.getUrl(request));

        }   catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    @PutMapping("*/{id}")
    public ModelAndView update(HttpServletRequest request,
                               @PathVariable UUID id,
                               RedirectAttributes redirectAttributes) {
        try {
            String url = hubUtils.getUrl(request);
            return hubUtils.setAttributes(id, redirectAttributes, url);

        }   catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("*/{id}")
    public ModelAndView delete(HttpServletRequest request, @PathVariable UUID id,
                               RedirectAttributes redirectAttributes) {
        try {

            String url = hubUtils.getUrl(request);
            return hubUtils.setAttributes(id, redirectAttributes, url);

        }   catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

}





