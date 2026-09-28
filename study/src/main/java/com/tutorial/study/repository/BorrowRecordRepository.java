package com.tutorial.study.repository;

import com.tutorial.study.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRecordRepository
            extends JpaRepository<BorrowRecord, Integer> {

    List<BorrowRecord> findByMemberId(int memberId);
    long countByMemberIdAndStatus(int memberId,String status);
    Optional<BorrowRecord> findByMemberIdAndBookIdAndStatus(int memberId, int bookId, String status);
}
