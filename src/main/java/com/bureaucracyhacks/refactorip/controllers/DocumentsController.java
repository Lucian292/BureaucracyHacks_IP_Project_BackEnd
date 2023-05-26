package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.models.DocumentJPA;
import com.bureaucracyhacks.refactorip.models.TaskJPA;
import com.bureaucracyhacks.refactorip.repositories.DocumentRepository;
import com.bureaucracyhacks.refactorip.repositories.TaskRepository;
import com.bureaucracyhacks.refactorip.services.TaskService;
import com.bureaucracyhacks.refactorip.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class DocumentsController {

    private final DocumentRepository documentRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @GetMapping("/{taskId}/documents")
    public ResponseEntity<List<DocumentJPA>> getDocumentsForTask(@PathVariable Long taskId) {
        List<DocumentJPA> documents = taskService.getDocumentsForTask(taskId);

        if (documents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(documents, HttpStatus.OK);
        }
    }


    @GetMapping("/get-doc-by-name/{taskName}")
    public ResponseEntity<List<DocumentJPA>> getDocumentsForTaskByName(@PathVariable String taskName) {
        List<DocumentJPA> documents = taskService.getDocumentsForTaskByName(taskName);

        if (documents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(documents, HttpStatus.OK);
        }
    }
    @PostMapping("/upload/byName/{name}")
    public String handleFileUpload(@PathVariable String name, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            taskService.uploadDocumentByName(name, file);
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/download/byName/{name}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String name) {
        try {
            ByteArrayResource resource = taskService.downloadDocumentByName(name);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + ".png\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload/byId/{document_id}")
    public String handleFileUpload(@PathVariable Integer document_id, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            taskService.handleFileUpload(document_id, file.getBytes());
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/download/byId/{document_id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer document_id) throws IOException {
        return taskService.downloadDocument(document_id);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Optional<TaskJPA>> getTaskById(@PathVariable(value = "id") Long taskId){
        Optional<TaskJPA> task = taskRepository.findById(taskId);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<Optional<TaskJPA>> GetTaskByName(@PathVariable String name){
        Optional<TaskJPA> task = taskRepository.findByName(name);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/names")
    public List<String> getAllTaskNames(){
        return taskRepository.findAll().stream().map(TaskJPA::getName).toList();
    }
}