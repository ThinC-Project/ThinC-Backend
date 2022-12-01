package com.thincbackend.service;

import com.thincbackend.domain.Bookmark;
import com.thincbackend.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public Bookmark saveBookmark(Bookmark bookmark){
        return bookmarkRepository.save(bookmark);
    }

    public List<Bookmark> findBookmarkByOwner(String owner){
        return bookmarkRepository.findByOwner(owner);
    }

    public void deleteBookmarkByRecipeIdAndOwner(Long recipeId, String owner){
        bookmarkRepository.deleteBookmarkByRecipeIdAndOwner(recipeId, owner);
    }
}
