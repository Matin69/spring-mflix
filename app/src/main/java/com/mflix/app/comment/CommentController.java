package com.mflix.app.comment;

import com.mflix.app.common.RestCrudController;
import com.mflix.app.common.RestExceptions;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController implements RestCrudController {

    private final CommentRepository mongoCommentRepository;

    public CommentController(CommentRepository mongoCommentRepository) {
        this.mongoCommentRepository = mongoCommentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> search() {
        return ResponseEntity.ok(
                mongoCommentRepository.findAll(Pageable.ofSize(10))
                        .stream()
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> findById(@PathVariable String id) {
        return ResponseEntity.ok(
                mongoCommentRepository
                        .findById(id)
                        .orElseThrow(() -> RestExceptions.resourceNotFoundException)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteById(@PathVariable String id) {
        Comment comment = mongoCommentRepository
                .findById(id)
                .orElseThrow(() -> RestExceptions.resourceNotFoundException);
        mongoCommentRepository.deleteById(id);
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Comment> save(@RequestBody CommentRequest saveRequest) {
        Comment comment = new Comment(
                saveRequest.id,
                saveRequest.name,
                saveRequest.email,
                saveRequest.movieId,
                saveRequest.text,
                new Date()
        );
        return ResponseEntity.ok(mongoCommentRepository.save(comment));
    }
}
