package com.example.Backend.Repositories;

import com.example.Backend.Model.Document;
import com.example.Backend.Model.DocumentId;
import com.example.Backend.Model.Pet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, DocumentId> {
    @Transactional
    void deleteByPet(Pet pet);
}