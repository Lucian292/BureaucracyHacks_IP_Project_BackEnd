package com.bureaucracyhacks.refactorip.services;

import com.bureaucracyhacks.refactorip.exceptions.TaskNotFoundException;
import com.bureaucracyhacks.refactorip.models.DocumentJPA;
import com.bureaucracyhacks.refactorip.models.TaskJPA;
import com.bureaucracyhacks.refactorip.repositories.DocumentRepository;
import com.bureaucracyhacks.refactorip.repositories.TaskRepository;
import com.bureaucracyhacks.refactorip.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private DocumentRepository documentRepository;
    public List<DocumentJPA> getDocumentsForTask(Long taskId) {
        TaskJPA task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        return task.getDocuments();
    }

    public List<DocumentJPA> getDocumentsForTaskByName(String taskName) {
        TaskJPA task = taskRepository.findByName(taskName)
                .orElseThrow(TaskNotFoundException::new);

        return task.getDocuments();
    }

    public void uploadDocumentByName(String name, MultipartFile file) throws IOException, IOException {
        DocumentJPA document = documentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setFile(ImageUtil.compressImage(file.getBytes()));

        documentRepository.save(document);
    }
    public ByteArrayResource downloadDocumentByName(String name) throws IOException {
        DocumentJPA document = documentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        byte[] decompressedFile = ImageUtil.decompressImage(document.getFile());

        return new ByteArrayResource(decompressedFile);
    }
    public void handleFileUpload(Integer document_id, byte[] fileBytes) throws IOException {
        DocumentJPA document = documentRepository.findById(document_id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        document.setFile(ImageUtil.compressImage(fileBytes));
        documentRepository.save(document);
    }
    public ResponseEntity<ByteArrayResource> downloadDocument(Integer documentId) throws IOException {
        DocumentJPA document = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));

        byte[] decompressedFile = ImageUtil.decompressImage(document.getFile());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + ".png\"")
                .body(new ByteArrayResource(decompressedFile));
    }

}
