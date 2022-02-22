package com.fernandoog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fernandoog.exception.ResourceNotFoundException;
import com.fernandoog.model.Script;
import com.fernandoog.repository.ScriptRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ScriptController {

  @Autowired
  private ScriptRepository scriptRepository;

  // create script rest api
  @PostMapping("/scripts")
  public Script createScript(@RequestBody Script script) {
    return scriptRepository.save(script);
  }

  // delete script rest api
  @DeleteMapping("/scripts/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteScript(@PathVariable Long id) {
    Script script = scriptRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Script not exist with id :" + id));

    scriptRepository.delete(script);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }

  // get all scripts
  @GetMapping("/scripts")
  public List<Script> getAllScripts() {
    return scriptRepository.findAll();
  }

  // get script by id rest api
  @GetMapping("/scripts/{id}")
  public ResponseEntity<Script> getScriptById(@PathVariable Long id) {
    Script script = scriptRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Script not exist with id :" + id));
    return ResponseEntity.ok(script);
  }

  // update script rest api
  @PutMapping("/scripts/{id}")
  public ResponseEntity<Script> updateScript(@PathVariable Long id, @RequestBody Script scriptDetails) {
    Script script = scriptRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Script not exist with id :" + id));

    script.setCode(scriptDetails.getCode());

    Script updatedScript = scriptRepository.save(script);
    return ResponseEntity.ok(updatedScript);
  }

  // Execute script by id rest api
  @GetMapping("/scripts/exec/{id}")
  public ResponseEntity<Script> launchScriptById(@PathVariable Long id) {
    Script script = scriptRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Script not exist with id :" + id));
    return ResponseEntity.ok(script);
  }


}
