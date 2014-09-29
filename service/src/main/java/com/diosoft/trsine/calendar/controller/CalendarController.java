package com.diosoft.trsine.calendar.controller;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.diosoft.trsine.calendar.service.CalendarService;
import com.diosoft.trsine.calendar.util.DateHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class CalendarController {

    public void setService(CalendarService service) {
        this.calendarService = service;
    }

    @Autowired
    private CalendarService calendarService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getPages() {

        ModelAndView model = new ModelAndView("calendar");
   		return model;
	}

	@RequestMapping(value = "/getEvents", method = RequestMethod.GET)
	public @ResponseBody
	String getEvents(@RequestParam String start, @RequestParam String end) throws RemoteException {
        String result = "";

         List<Event> list = calendarService.searchByInterval(
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

    @RequestMapping(value = "/addEvent", method = RequestMethod.GET)
    public @ResponseBody
    String addEvent(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd' 'HH:mm:ss") Date start,
                    @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd' 'HH:mm:ss") Date end,
                    @RequestParam String description,
                    @RequestParam String attenders,
                    @RequestParam String title
    ) throws RemoteException {
        String result = "{\"result\":true}";

        //TODO: attenders from Event

        Event event = null;
        try {
            event = new Event.HashSetBuilder()
                    .setDateBegin(start)
                    .setDateEnd(end)
                    .setId(UUID.randomUUID())
                    .setTitle(title)
                    .setDescription(description)
                    .build();
        } catch (IdIsNullException e) {
            e.printStackTrace();
        } catch (DateIntervalIsIncorrectException e) {
            e.printStackTrace();
        }

        calendarService.add(event);

        return result;
    }

    @RequestMapping(value = "/updateEventOnDrop", method = RequestMethod.GET)
    public @ResponseBody
    String updateEventOnDrop(@RequestParam String id, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") Date start) throws RemoteException {
        String result = "{\"result\":true}";

        Event oldEvent = calendarService.getById(UUID.fromString(id));
        long offset = oldEvent.getDateBegin().getTime() - start.getTime();

        Event event;
        try {
            event = new Event.HashSetBuilder(oldEvent)
                    .setDateBegin(new Date(oldEvent.getDateBegin().getTime() - offset))
                    .setDateEnd(new Date(oldEvent.getDateEnd().getTime() - offset))
                    .build();
        } catch (IdIsNullException e) {
            result = "{\"result\":false}";
            e.printStackTrace();
            return result;

        } catch (DateIntervalIsIncorrectException e) {
            result = "{\"result\":false}";
            e.printStackTrace();
            return result;
        }

        calendarService.remove(UUID.fromString(id));
        calendarService.add(event);

        return result;
    }
}
