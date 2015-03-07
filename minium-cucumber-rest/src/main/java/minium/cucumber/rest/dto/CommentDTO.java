/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.cucumber.rest.dto;

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
