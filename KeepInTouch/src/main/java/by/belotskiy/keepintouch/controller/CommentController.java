package by.belotskiy.keepintouch.controller;

import by.belotskiy.keepintouch.dto.CommentDto;
import by.belotskiy.keepintouch.dto.mapper.DtoMapper;
import by.belotskiy.keepintouch.exception.NoSuchCommentException;
import by.belotskiy.keepintouch.exception.NoSuchNewsException;
import by.belotskiy.keepintouch.model.Comment;
import by.belotskiy.keepintouch.service.CommentService;
import by.belotskiy.keepintouch.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/news/{newsId}")
public class CommentController {

    private final NewsService newsService;
    private final CommentService commentService;
    private final DtoMapper dtoMapper;

    public CommentController(NewsService newsService, CommentService commentService, DtoMapper dtoMapper) {
        this.newsService = newsService;
        this.commentService = commentService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/comments")
    public CommentDto addComment(@PathVariable (value = "newsId") Integer newsId,
                                 @RequestBody Comment comment) {
        comment.setPublishedAt(Instant.now());
        return null;
        /* return newsService.findById(newsId)
                .map(news -> {
                    comment.setNews(news);
                    return dtoMapper.mapToDto(commentService.saveComment(comment));
                }).orElseThrow(() -> new NoSuchNewsException("NewsId " + newsId + " not found"));*/
    }

    @GetMapping("/comments/{commentId}")
    public CommentDto showComment(@PathVariable(name = "newsId") Integer newsId,
                                  @PathVariable(name = "commentId") Integer commentId) {
        if (commentService.findComment(commentId).isEmpty()) {
            throw new NoSuchCommentException("There is no comments with ID = " + commentId);
        } else {
            return dtoMapper.mapToDto(commentService.findComment(commentId).get());
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "newsId") Integer newsId,
                                                @PathVariable(name = "commentId") Integer commentId) {
        if (commentService.findComment(commentId).isEmpty()) {
            throw new NoSuchCommentException("There is no comments with ID = " + commentId);
        } else {
            commentService.deleteCommentById(commentId);
            return new ResponseEntity<>("Comment with ID " + commentId + " was successfully deleted",
                    HttpStatus.NOT_FOUND) ;
        }
    }

    @PutMapping("/comments")
    public CommentDto updateComment(@PathVariable(name = "newsId") Integer newsId,
                                    Comment comment) {
        if (commentService.findComment(comment.getId()).isEmpty()) {
            throw new NoSuchCommentException("There is no comments with ID = " + comment.getId());
        }
        comment.setPublishedAt(Instant.now());
        return dtoMapper.mapToDto(commentService.saveComment(comment));
    }
    //TODO add GET comments/{newsId}/

}
