package ru.ewm.service.util.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ewm.service.category.general.model.Category;
import ru.ewm.service.category.general.repository.CategoryRepository;
import ru.ewm.service.compilation.general.model.Compilation;
import ru.ewm.service.compilation.general.repository.CompilationRepository;
import ru.ewm.service.util.exception.NotFoundException;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.event.general.repository.EventRepository;
import ru.ewm.service.participation_request.model.ParticipationRequest;
import ru.ewm.service.participation_request.repository.RequestRepository;
import ru.ewm.service.user.model.User;
import ru.ewm.service.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntityValidator {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final CompilationRepository compilationRepository;

    public User checkIfUserExist(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new NotFoundException(userId, User.class.getSimpleName()));
    }

    public Event checkIfEventExist(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        return event.orElseThrow(() -> new NotFoundException(eventId, Event.class.getSimpleName()));
    }

    public Category checkIfCategoryExist(Long catId) {
        Optional<Category> category = categoryRepository.findById(catId);
        return category.orElseThrow(() -> new NotFoundException(catId, Category.class.getSimpleName()));
    }

    public ParticipationRequest checkIfRequestExist(Long requestId) {
        Optional<ParticipationRequest> request = requestRepository.findById(requestId);
        return request
                .orElseThrow(() -> new NotFoundException(requestId, ParticipationRequest.class.getSimpleName()));
    }

    public Compilation checkIfCompilationExist(Long compId) {
        Optional<Compilation> compilation = compilationRepository.findById(compId);
        return compilation.orElseThrow(() -> new NotFoundException(compId, Compilation.class.getSimpleName()));
    }
}
