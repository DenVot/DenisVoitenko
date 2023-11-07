package org.denvot.news.controllers.responses;

import java.util.List;
import java.util.Set;

public record ArticleResponse(long id, String name, Set<String> tags,
                              List<CommentResponse> comments) {
}
