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
    public ResponseEntity<String> createBindings(@RequestBody List<BindingDTO> bindingDTOs) {
        int addedCount = 0;
        int duplicateCount = 0;

        for (BindingDTO dto : bindingDTOs) {
            boolean exists = bindingRepository.existsByLeague1NameAndLeague2NameAndHomeTeam1NameAndHomeTeam2NameAndAwayTeam1NameAndAwayTeam2Name(
                    dto.getLeague1Name(),
                    dto.getLeague2Name(),
                    dto.getHomeTeam1Name(),
                    dto.getHomeTeam2Name(),
                    dto.getAwayTeam1Name(),
                    dto.getAwayTeam2Name()
            );

            if (!exists) {
                Binding binding = new Binding(
                        null,
                        dto.getLeague1Name(),
                        dto.getLeague2Name(),
                        dto.getHomeTeam1Name(),
                        dto.getHomeTeam2Name(),
                        dto.getAwayTeam1Name(),
                        dto.getAwayTeam2Name(),
                        dto.getDataSource1(), // 设置 dataSource1
                        dto.getDataSource2()  // 设置 dataSource2
                );
                bindingRepository.save(binding);
                addedCount++;
            } else {
                duplicateCount++;
            }
        }

        String resultMessage = String.format("成功添加: %d 条记录, 重复: %d 条记录", addedCount, duplicateCount);
        return ResponseEntity.ok(resultMessage);
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
                        binding.getAwayTeam2Name(),
                        binding.getDataSource1(),
                        binding.getDataSource2()
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
