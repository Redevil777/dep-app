package com.app.controller;

import com.app.model.Role;
import com.app.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by andrey on 22.08.16.
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final String USER_REST = "http://localhost:9000/user/";
    private final String ROLE_REST = "http://localhost:9000/role/";

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('All_USERS_GET')")
    public ModelAndView getAllUsers(){
        ModelAndView view = new ModelAndView("user");

        RestTemplate restTemplate = new RestTemplate();

        try {
            User[] users = restTemplate.getForObject(USER_REST + "/all", User[].class);

            Role[] roles = restTemplate.getForObject(ROLE_REST + "/all", Role[].class);

            view.addObject("users", users);
            view.addObject("roles", roles);
            view.addObject("user", new User());
        } catch (Exception e){
        }
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADD_USER_GET')")
    public ModelAndView getAddUser(){
        ModelAndView view = new ModelAndView("userAdd");

        RestTemplate restTemplate = new RestTemplate();

        Role[] roles = restTemplate.getForObject(ROLE_REST + "/all", Role[].class);

        view.addObject("user", new User());
        view.addObject("roles11", roles);
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADD_USER_POST')")
    public ModelAndView AddUser(RedirectAttributes redirectAttributes,
                                @RequestParam("username") String name,
                                @RequestParam("password") String password,
                                @RequestParam("roles") Set<Role> role){

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();


        ArrayList<String> roles = new ArrayList<>();

        for (Role q: role){
            roles.add(q.getRoleName());
        }

        map.add("username", name);
        map.add("password", password);
        map.add("role", roles);


        try {
            restTemplate.postForObject(USER_REST + "/add",map, String.class);
            return new ModelAndView("redirect:/user/all");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "User can not add.");

            return new ModelAndView("redirect:/user/all");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('DELETE_USER_GET')")
    public ModelAndView deleteUserById(RedirectAttributes redirectAttributes,
                                             @PathVariable long id) {

        ModelAndView view = new ModelAndView("redirect:/user/all");

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.delete(USER_REST + "/delete/" + id);

            redirectAttributes.addFlashAttribute("message", "User deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute( "error", "Can't remove user with id = " + id);
        }
        return view;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('EDIT_USER_GET')")
    public ModelAndView getEdit(@PathVariable long id){
        ModelAndView view = new ModelAndView("userEdit");

        try {
            RestTemplate restTemplate = new RestTemplate();
            User user = restTemplate.getForObject(USER_REST + "/id/" + id, User.class);
            Role[] role = restTemplate.getForObject(ROLE_REST + "/all", Role[].class);


            Set<Role> roles = user.getRoles();
            List<String> roleName = new ArrayList<>();
            for (Role qwe:roles){
               roleName.add(qwe.getRoleName());
            }
            view.addObject("editUser", user);
            view.addObject("roleName", roleName.get(0));
            view.addObject("roles", role);
        } catch (Exception e){

        }

        return view;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('EDIT_USER_POST')")
    public ModelAndView saveEditUser(RedirectAttributes redirectAttributes,
                                           @RequestParam("id")   String   id,
                                           @RequestParam("username") String name,
                                           @RequestParam("password") String password,
                                           @RequestParam("roles") String role){
        ModelAndView view = new ModelAndView("redirect:/user/all");

        RestTemplate restTemplate = new RestTemplate();

        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("id", id);
            map.add("username", name);
            map.add("password", password);
            map.add("role", role);
            restTemplate.postForObject(USER_REST + "/edit", map, String.class);

            redirectAttributes.addFlashAttribute("message", "User edited");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Can't edit user whit id =" + id);
        }
        return view;
    }
}
