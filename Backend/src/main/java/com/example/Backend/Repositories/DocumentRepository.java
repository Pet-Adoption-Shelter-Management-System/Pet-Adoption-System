package com.example.Backend.Repositories;

import com.example.Backend.Model.Document;
import com.example.Backend.Model.DocumentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, DocumentId> {
}