package com.booking.app.controller;

import com.booking.app.dtos.BlockDto;
import com.booking.app.dtos.UpdateBlockDto;
import com.booking.app.models.BlockModel;
import com.booking.app.models.PropertyModel;
import com.booking.app.services.BlockService;
import com.booking.app.services.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<Object> createBlock(@RequestBody @Valid BlockDto blockDto) {
        try {

            var blockModel = new BlockModel();
            BeanUtils.copyProperties(blockDto, blockModel);
            Optional<PropertyModel> property = propertyService.getPropertyByName(blockDto.getPropertyName());
            blockModel.setProperty(property.get());
            blockService.saveBlock(blockModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(blockModel);
        } catch (IllegalArgumentException e) {
            // Return 400 if there are overlapping bookings, with a custom error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBlock(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateBlockDto updateBlockDto) {
        try {
            Optional<BlockModel> block = blockService.findBlockById(id);
            if (block == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }
            var blockModel = block.get();
            blockModel.setStartDate(updateBlockDto.getStartDate());
            blockModel.setEndDate(updateBlockDto.getEndDate());
            blockService.saveBlock(blockModel);
            return ResponseEntity.status(HttpStatus.OK).body(blockModel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBlock(@PathVariable UUID id) {
        try {
            blockService.deleteBlock(id);
            return ResponseEntity.status(HttpStatus.OK).body("Block deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
