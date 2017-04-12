package com.config.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.config.services.PersonService;
import com.config.validator.PersonFormValidator;
import com.entities.Person;

@Controller
public class IndexController {
	private final Logger log = Logger.getLogger(IndexController.class);
	@Autowired
	private PersonService personService;
	
	@Autowired
	private PersonFormValidator personFormValidator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(personFormValidator);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		log.info("Enter into Login Controller...!");
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView getPersonList() {
		log.info("Getting All List Person...!");
		return new ModelAndView("index", "personList", personService.listPersons());
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String saveOrupdate(@ModelAttribute("personData") @Validated Person person,BindingResult result,final RedirectAttributes redirect){
		log.info("Enter into Save Or Update Person Details Action...!");
		personFormValidator.validate(person, result);
		if(result.hasErrors()) {
			return "redirect:/index/add";
		} else {
			if(StringUtils.isEmpty(person.getId())) {
				personService.addPerson(person);
				redirect.addFlashAttribute("msg", "Person added successfully!");
			} else {
				personService.updatePerson(person);
				redirect.addFlashAttribute("msg", "Person Update successfully!");
			}
			return "redirect:/index/"+person.getId();
		}
	}
	
	@RequestMapping(value = "/index/{id}", method = RequestMethod.GET)
	public ModelAndView getPersonDetails(@PathVariable("id") Integer id) {
		log.info("Getting Person By Id...!");
		return new ModelAndView("person", "person",	personService.getPersonById(id));
	}

	@RequestMapping(value = "/index/add", method = RequestMethod.GET)
	public ModelAndView addPerson() {
		log.info("Enter into Adding New Person Form...!");
		return new ModelAndView("personForm","personData",new Person());
	}
	
	@RequestMapping(value = "/index/{id}/update", method = RequestMethod.GET)
	public ModelAndView updatePerson(@PathVariable("id") Integer id){
		log.info("Enter into Updating Person Details...!");
		return new ModelAndView("personForm","personData",personService.getPersonById(id));
	}
	
	@RequestMapping(value = "/index/{id}/delete", method = RequestMethod.POST)
	public String deletePerson(@PathVariable("id") Integer id, final RedirectAttributes redirect) {
		if(!StringUtils.isEmpty(id)) {
			personService.removePerson(id);
			redirect.addFlashAttribute("msg", "Person deleted successfully!");
		}
		return "redirect:/index";
	}
}