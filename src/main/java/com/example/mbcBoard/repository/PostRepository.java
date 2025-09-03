package com.example.mbcBoard.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mbcBoard.domain.Post;
import com.example.mbcBoard.domain.PostLikeCount;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	// select ... where id like '%?%'
	Page<Post> findByTitleContaining(String keyword, Pageable pageable);
	Page<Post> findByContentContaining(String keyword, Pageable pageable);
	Page<Post> findByUser_UsernameContaining(String keyword, Pageable pageable);
	
	 // 내가 이 글을 좋아요 눌렀는지
    boolean existsByIdAndLikers_Id(Integer postId, Integer userId);

    // 좋아요 개수 (JPQL, 조인 테이블 카운트)
    @Query("select count(u) from Post p join p.likers u where p.id = :postId")
    long countLikers(@Param("postId") Integer postId);

    // 언라이크를 리스트 전체 로딩 없이 한 방에 처리 (선택)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from post_liker where post_id = :postId and user_id = :userId", nativeQuery = true)
    int deleteLikerLink(@Param("postId") Integer postId, @Param("userId") Integer userId);
    
    @Query(value = "SELECT post_id as postId, COUNT(post_id) as duplicates " +
            "FROM post_liker " +
            "GROUP BY post_id " +
            "ORDER BY duplicates DESC "+
            "LIMIT 5",
    nativeQuery = true)
   List<PostLikeCount> findPostLikeCounts();
    
}
