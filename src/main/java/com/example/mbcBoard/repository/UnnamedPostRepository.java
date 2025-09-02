package com.example.mbcBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mbcBoard.domain.UnnamedPost;

@Repository
public interface UnnamedPostRepository extends JpaRepository<UnnamedPost, Integer>{
	
	// 내가 이 글을 좋아요 눌렀는지
    boolean existsByIdAndLikers_Id(Integer postId, Integer userId);

    // 좋아요 개수 (JPQL, 조인 테이블 카운트)
    @Query("select count(u) from UnnamedPost p join p.likers u where p.id = :postId")
    long countLikers(@Param("postId") Integer postId);

    // 언라이크를 리스트 전체 로딩 없이 한 방에 처리 (선택)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from unnamed_post_liker where unnamed_post_id = :postId and user_id = :userId", nativeQuery = true)
    int deleteLikerLink(@Param("postId") Integer postId, @Param("userId") Integer userId);

}
