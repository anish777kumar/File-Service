package com.bunnu.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bunnu.entity.Documents;

/**
 * The Interface DocumentsRepository.
 */
@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {

}
