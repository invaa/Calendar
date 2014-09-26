package com.diosoft.trsine.calendar.controller;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.context.ApplicationContextProvider;
import com.diosoft.trsine.calendar.service.CalendarServiceImpl;
import com.diosoft.trsine.calendar.util.DateHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CalendarController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getPages() {

        ModelAndView model = new ModelAndView("calendar");
   		return model;
	}

	@RequestMapping(value = "/getEvents", method = RequestMethod.GET)
	public @ResponseBody
	String getEvents(@RequestParam String start, @RequestParam String end) {
        String result = "";

        CalendarServiceImpl service = ApplicationContextProvider.getApplicationContext().getBean("calendarService", CalendarServiceImpl.class);

        List<Event> list = service.searchByInterval(
                DateHelper.getDateFromString(start, "yyyy-MM-dd"),
                DateHelper.getDateFromString(end, "yyyy-MM-dd"))
                .parallelStream()
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));

        try {
            result = mapper.writeValueAsString(list);
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
	}

}
