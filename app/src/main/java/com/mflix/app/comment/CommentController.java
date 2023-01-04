package com.mflix.app.comment;

import com.mflix.app.common.RestCrudController;
import com.mflix.app.common.RestExceptions;
import com.mflix.app.common.RestResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController implements RestCrudController<Comment, CommentRequest, String> {

    private final CommentRepository mongoCommentRepository;

    public CommentController(CommentRepository mongoCommentRepository) {
        this.mongoCommentRepository = mongoCommentRepository;
    }

    @Override
    @GetMapping
    public ResponseEntity<RestResponse<List<Comment>>> search() {
        List<Comment> comments = mongoCommentRepository.findAll(Pageable.ofSize(10))
                .stream()
                .toList();
        RestResponse<List<Comment>> response = new RestResponse<>(true, "search", comments);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<Comment>> findById(@PathVariable String id) {
        Comment comment = mongoCommentRepository
                .findById(id)
                .orElseThrow(() -> RestExceptions.resourceNotFoundException);
        RestResponse<Comment> response = new RestResponse<>(true, "get", comment);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Comment>> deleteById(@PathVariable String id) {
        Comment comment = mongoCommentRepository
                .findById(id)
                .orElseThrow(() -> RestExceptions.resourceNotFoundException);
        mongoCommentRepository.deleteById(id);
        RestResponse<Comment> response = new RestResponse<>(true, "delete", comment);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    @Transactional
    public ResponseEntity<RestResponse<Comment>> save(@RequestBody CommentRequest saveRequest) {
        Comment comment = new Comment(
                saveRequest.id,
                saveRequest.name,
                saveRequest.email,
                saveRequest.movieId,
                saveRequest.text,
                new Date()
        );
        Comment savedComment = mongoCommentRepository.save(comment);
        RestResponse<Comment> response = new RestResponse<>(true, "save", savedComment);
        return ResponseEntity.ok(response);
    }
}
