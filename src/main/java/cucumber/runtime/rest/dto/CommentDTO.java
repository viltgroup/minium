package cucumber.runtime.rest.dto;

import gherkin.formatter.model.Comment;

import java.util.List;

import com.google.common.collect.Lists;

public class CommentDTO {

    private String value;
    private Integer line;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        this.value = comment.getValue();
        this.line = comment.getLine();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Comment toComment() {
        return new Comment(value, line);
    }

    public static List<Comment> toGherkinComments(List<CommentDTO> comments) {
        if (comments == null) return null;
        List<Comment> gherkinComments = Lists.newArrayList();
        for (CommentDTO comment : comments) {
            gherkinComments.add(comment.toComment());
        }
        return gherkinComments;
    }

    public static List<CommentDTO> fromGherkinComments(List<Comment> gherkinComments) {
        if (gherkinComments == null) return null;
        List<CommentDTO> comments = Lists.newArrayList();
        for (Comment gherkinComment : gherkinComments) {
            comments.add(new CommentDTO(gherkinComment));
        }
        return comments;
    }

}
