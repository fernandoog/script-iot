package com.fernandoog.controller;

import com.fernandoog.exception.ResourceNotFoundException;
import com.fernandoog.model.Script;
import com.fernandoog.repository.ScriptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ScriptController {

    public static final String SCRIPT_NOT_EXIST_WITH_ID = "Script not exist with id :";
    Logger log = LoggerFactory.getLogger(ScriptController.class);

    @Autowired
    private ScriptRepository scriptRepository;

    // create script rest api
    @PostMapping("/scripts")
    public Script createScript(@RequestBody Script script) {
        // TODO scriptDTO
        return scriptRepository.save(script);
    }

    // delete script rest api
    @DeleteMapping("/scripts/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteScript(@PathVariable Long id) {
        Script script = scriptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SCRIPT_NOT_EXIST_WITH_ID + id));

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
                .orElseThrow(() -> new ResourceNotFoundException(SCRIPT_NOT_EXIST_WITH_ID + id));
        return ResponseEntity.ok(script);
    }

    // update script rest api
    @PutMapping("/scripts/{id}")
    public ResponseEntity<Script> updateScript(@PathVariable Long id, @RequestBody Script scriptDetails) {
        // TODO scriptDetailsDTO
        Script script = scriptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SCRIPT_NOT_EXIST_WITH_ID + id));

        script.setCode(scriptDetails.getCode());

        Script updatedScript = scriptRepository.save(script);
        return ResponseEntity.ok(updatedScript);
    }

    // Execute script by id rest api
    @GetMapping("/scripts/exec/{id}")
    public ResponseEntity<Script> launchScriptById(@PathVariable Long id) {

        Script script = scriptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SCRIPT_NOT_EXIST_WITH_ID + id));

        StringBuilder result = new StringBuilder();
        try {
            Process child = Runtime.getRuntime().exec(script.getCode());
            DataInputStream in = new DataInputStream(
                    child.getInputStream());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            script.setResult(e.getMessage());
        }

        script.setResult(result.toString());

        return ResponseEntity.ok(script);
    }

}
