package com.thincbackend.service;

import com.thincbackend.domain.Bookmark;
import com.thincbackend.repository.BookmarkRepository;

import java.util.List;

public class BookmarkService {
    private BookmarkRepository bookmarkRepository;

    public Bookmark saveBookmark(Bookmark bookmark){
        return bookmarkRepository.save(bookmark);
    }

    public List<Bookmark> findBookmarkByOwner(String owner){
        return bookmarkRepository.findByOwner(owner);
    }

    public void deleteBookmarkByRecipeIdAndOwner(Long recipeId, String owner){
        bookmarkRepository.deleteBookmarkByRecipe_idAndOwner(recipeId, owner);
    }
}
