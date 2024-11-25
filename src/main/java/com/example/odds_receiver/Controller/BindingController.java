// src/main/java/com/example/odds_receiver/Controller/BindingController.java

package com.example.odds_receiver.Controller;

import com.example.odds_receiver.DTO.BindingDTO;
import com.example.odds_receiver.DTO.BindingRecordDTO;
import com.example.odds_receiver.Model.Binding;
import com.example.odds_receiver.Repository.BindingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bindings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 根据需要调整CORS策略
public class BindingController {

    private final BindingRepository bindingRepository;

    // 创建新的绑定记录
    @PostMapping
    public ResponseEntity<Binding> createBinding(@RequestBody BindingDTO bindingDTO) {
        // 添加日志输出
        System.out.println("Received BindingDTO:");
        System.out.println("League1Name: " + bindingDTO.getLeague1Name());
        System.out.println("League2Name: " + bindingDTO.getLeague2Name());
        System.out.println("HomeTeam1Name: " + bindingDTO.getHomeTeam1Name());
        System.out.println("HomeTeam2Name: " + bindingDTO.getHomeTeam2Name());
        System.out.println("AwayTeam1Name: " + bindingDTO.getAwayTeam1Name());
        System.out.println("AwayTeam2Name: " + bindingDTO.getAwayTeam2Name());

        Binding binding = new Binding(
                null, // ID 自动生成
                bindingDTO.getLeague1Name(),
                bindingDTO.getLeague2Name(),
                bindingDTO.getHomeTeam1Name(),
                bindingDTO.getHomeTeam2Name(),
                bindingDTO.getAwayTeam1Name(),
                bindingDTO.getAwayTeam2Name()
        );
        Binding savedBinding = bindingRepository.save(binding);
        return ResponseEntity.ok(savedBinding);
    }

    // 获取所有绑定记录
    @GetMapping
    public ResponseEntity<List<BindingRecordDTO>> getAllBindings() {
        List<Binding> bindings = bindingRepository.findAll();
        List<BindingRecordDTO> bindingDTOs = bindings.stream()
                .map(binding -> new BindingRecordDTO(
                        binding.getId(),
                        binding.getLeague1Name(),
                        binding.getLeague2Name(),
                        binding.getHomeTeam1Name(),
                        binding.getHomeTeam2Name(),
                        binding.getAwayTeam1Name(),
                        binding.getAwayTeam2Name()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bindingDTOs);
    }

    // 删除指定ID的绑定记录
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBinding(@PathVariable Long id) {
        if (!bindingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bindingRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
