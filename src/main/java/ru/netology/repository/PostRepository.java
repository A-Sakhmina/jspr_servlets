package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

    private final ConcurrentHashMap<Long, Post> postsRepository = new ConcurrentHashMap<>();
    static AtomicLong postId = new AtomicLong(0);

    public List<Post> all() {
        if (!postsRepository.isEmpty()) return new ArrayList<>(postsRepository.values());
        else throw new NullPointerException("the repository is empty");
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postsRepository.get(id));
    }

    public Post save(Post post) throws NotFoundException {

        if (post.getId() == 0) {
            //create new post
            post.setId(postId.get());
            postId.getAndIncrement();
        } else if (postsRepository.containsKey(post.getId())) {
            //update id number of the post
            postsRepository.put(post.getId(), post);
        } else throw new NotFoundException("Post with that id doesn't exist");
        return post;
    }

    public void removeById(long id) throws NotFoundException {
        if (postsRepository.containsKey(id)) {
            postsRepository.remove(id);
        } else {
            throw new NotFoundException("Post with that id doesn't exist");
        }
    }
}
