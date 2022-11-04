package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomCreateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomDto;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomUpdateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.SortType;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/conferenceRooms")
@RequiredArgsConstructor
public class ConferenceRoomController {

    private final ConferenceRoomService conferenceRoomService;

    @GetMapping
    public List<ConferenceRoomDto> getList(@RequestParam(required = false) SortType sortType) {
        log.info("Methode getListOfConferenceRoom was called");
        return conferenceRoomService.getList(sortType);
    }

    @GetMapping("/{name}")
    public List<ConferenceRoomDto> getByName(@RequestParam String name) {
        log.info("Methode getListOfConferenceRoom was called");
        return conferenceRoomService.getByName(name);
    }

    @PostMapping
    public ConferenceRoomDto create(@Valid @RequestBody ConferenceRoomCreateRequest request){
        log.info("Methode createConferenceRoom was called");
        return conferenceRoomService.create(request);
    }

    @PutMapping
    public ConferenceRoomDto update(@RequestBody ConferenceRoomUpdateRequest request){
        log.info("Methode updateConferenceRoom was called");
        return conferenceRoomService.update(request);
    }

    @DeleteMapping("/{id}")
    public ConferenceRoomDto delete(@PathVariable(name = "id") Long conferenceRoomId){
        log.info("Methode deleteConferenceRoom was called");
        return conferenceRoomService.delete(conferenceRoomId);
    }
}
