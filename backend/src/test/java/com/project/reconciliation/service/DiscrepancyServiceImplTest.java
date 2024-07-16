package com.project.reconciliation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.reconciliation.entities.Discrepancy;
import com.project.reconciliation.exceptions.DiscrepancyNotFoundException;
import com.project.reconciliation.repositories.DiscrepancyRepository;

@ExtendWith(MockitoExtension.class)
public class DiscrepancyServiceImplTest {

    @Mock
    private DiscrepancyRepository discrepancyRepository;

    @InjectMocks
    private DiscrepancyServiceImpl discrepancyService;

    private Discrepancy discrepancy;

    @BeforeEach
    void setUp() {
        discrepancy = new Discrepancy();
        discrepancy.setDiscrepancyId(1);
        discrepancy.setDescription("Sample discrepancy");
    }

    @Test
    void getAllDiscrepancy_Positive() throws DiscrepancyNotFoundException {
        List<Discrepancy> discrepancies = List.of(discrepancy);
        when(discrepancyRepository.findAll()).thenReturn(discrepancies);

        List<Discrepancy> result = discrepancyService.getAllDiscrepancy();

        assertEquals(1, result.size());
        assertEquals("Sample discrepancy", result.get(0).getDescription());
    }

    @Test
    void getAllDiscrepancy_Negative() {
        when(discrepancyRepository.findAll()).thenReturn(Collections.emptyList());

        DiscrepancyNotFoundException exception = assertThrows(DiscrepancyNotFoundException.class, () -> {
            discrepancyService.getAllDiscrepancy();
        });

        assertEquals("No discrepancies found", exception.getMessage());
    }
}
