package smu.blog.post;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private List<Post> postList = new ArrayList<>();
    private Long sequence = 0L;

    public String createPost(PostCreateRequest postCreateRequest) {
        sequence += 1;
        String title = postCreateRequest.title();
        String content = postCreateRequest.content();

        Post post = new Post(sequence, title, content);
        postList.add(post);

        return "저장 완료";
    }

    public Post getPost(Long id) {
        for (Post post : postList) {
            if (post.getPostId().equals(id)) {
                return post;
            }
        }
        return null;
    }

    public List<Post> getPosts() {
        return postList;
    }


}
