package com.booking.app.services;

import com.booking.app.models.BlockModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.booking.app.repositories.BlockRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    //
    public BlockModel saveBlock(BlockModel block) {
        return blockRepository.save(block);
    }

    public void deleteBlock(UUID id) {
        blockRepository.deleteById(id);
    }

    public List<BlockModel> findByDateRange(LocalDate startDate, LocalDate endDate, UUID propertyId) {
        return blockRepository.findByDateRange(startDate, endDate, propertyId);
    }

    public Optional<BlockModel> findBlockById(UUID id) {
        return blockRepository.findById(id);
    }
}
