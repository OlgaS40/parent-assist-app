package com.parentapp.backend.services;

import com.parentapp.backend.child.Child;
import com.parentapp.backend.child.ChildDTO;
import com.parentapp.backend.child.ChildRepository;
import com.parentapp.backend.child.ChildService;
import com.parentapp.backend.fixture.ChildDTOFixture;
import com.parentapp.backend.fixture.ChildFixture;
import com.parentapp.backend.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChildServiceTest {
    @Mock
    private ChildRepository childRepository;
    @InjectMocks
    private ChildService childService;

    private Child testChild;
    private ChildDTO testChildDTO;
    private List<Child> testChildren;
    private List<ChildDTO> testChildDTOs;

    @BeforeEach
    public void setup() {
        testChild = ChildFixture.testChild();
        testChildDTO = ChildDTOFixture.testChild();
        testChildren = ChildFixture.testChildren();
        testChildDTOs = ChildDTOFixture.testChildDTOs();
    }
    @Test
    void findAll_whenInvoked_returnsAListOfTwoChildren(){
        when(childRepository.findAll()).thenReturn(testChildren);
        assertThat(childService.findAll())
                .isNotEmpty()
                .containsExactlyInAnyOrderElementsOf(testChildDTOs);
    }
    @Test
    void get_childId_returnChildDTO(){
        when(childRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(testChild));

        ChildDTO childDTO = childService.get("testChild01");
        assertThat(childDTO).isNotNull();
        assertThat(childDTO).isEqualTo(testChildDTO);
    }

    @Test
    void get_NonExistentId_throwNotFoundException() {
        when(childRepository.findById(anyString())).thenReturn(Optional.empty());
        String id = testChild.getId();

        assertThrows(NotFoundException.class, () -> childService.get(id));
        verify(childRepository, times(1)).findById(id);
    }
    @Test
    void create_whenInvoked_returnsUserId() {
        when(childRepository.save(any(Child.class))).thenReturn(testChild);

        String createdChildId = childService.create(testChildDTO);

        verify(childRepository, times(1)).save(any(Child.class));
        assertThat(createdChildId).isNotNull();
        assertThat(createdChildId).isEqualTo(testChild.getId());
    }
    @Test
    void update_childIdAndChildDTO_updatedChild() {
        ChildDTO updatedChildDTO = testChildDTO;
        String id = updatedChildDTO.getId();
        updatedChildDTO.setName("Alexandru");
        when(childRepository.findById(anyString())).thenReturn(Optional.of(testChild));

        Child updatedChild = testChild;
        updatedChild.setName("Alexandru");

        childService.update(id, updatedChildDTO);

        verify(childRepository, times(1)).findById(id);
        verify(childRepository, times(1)).save(updatedChild);
    }
    @Test
    void update_NonExistentChild_throwsNotFoundException() {
        when(childRepository.findById(anyString())).thenReturn(Optional.empty());

        ChildDTO updatedChildDTO = testChildDTO;
        String id = updatedChildDTO.getId();
        Child updatedChild = testChild;

        assertThrows(NotFoundException.class, () -> childService.update(id, updatedChildDTO));

        verify(childRepository, times(1)).findById(id);
        verify(childRepository, times(0)).save(updatedChild);
    }
    @Test
    void delete_childId_deletedChild() {
        childService.delete(testChild.getId());
        verify(childRepository, times(1)).deleteById(testChild.getId());
    }

}
