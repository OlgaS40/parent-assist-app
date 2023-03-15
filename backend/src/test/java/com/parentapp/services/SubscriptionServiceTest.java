package com.parentapp.services;

import com.parentapp.child.ChildRepository;
import com.parentapp.doubles.*;
import com.parentapp.fixture.SubscriptionDTOFixture;
import com.parentapp.fixture.SubscriptionFixture;
import com.parentapp.subscription.*;
import com.parentapp.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubscriptionServiceTest {
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private ChildRepository childRepository;
    private Subscription testSubscription;
    private SubscriptionDTO testSubscriptionDTO;
    private List<SubscriptionDTO> testSubscriptionDTOList;

    @BeforeEach
    public void setup() {
        testSubscription = SubscriptionFixture.getTestSubscription();
        testSubscriptionDTO = SubscriptionDTOFixture.getTestSubscriptionDTO();
        testSubscriptionDTOList = SubscriptionDTOFixture.testSubscriptionDTOs();
    }

    @Test
    void findAll_whenInvoked_returnsAListWithTwoSubscriptions() {
        subscriptionRepository = new SubscriptionRepositoryStub();
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, childRepository);

        assertThat(subscriptionService.findAll())
                .isNotEmpty()
                .containsExactlyInAnyOrderElementsOf(testSubscriptionDTOList);
    }

    @Test
    void get_subscriptionId_returnsSubscriptionDTO() {
        subscriptionRepository = new SubscriptionRepositoryMock();
        subscriptionRepository.save(testSubscription);
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, childRepository);

        SubscriptionDTO subscriptionDTO = subscriptionService.get("testSubscription01");
        assertThat(subscriptionDTO).isNotNull();
        assertThat(subscriptionDTO).isEqualTo(testSubscriptionDTO);
    }

    @Test
    void get_NonExistentId_throwNotFoundException() {
        subscriptionRepository = new SubscriptionRepositoryMock();
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, childRepository);

        assertThrows(NotFoundException.class, () -> subscriptionService.get("fakeId"));
    }

    @Test
    void create_whenInvoked_returnsSubscriptionId() {
        subscriptionRepository = new SubscriptionRepositoryFake();
        childRepository = new ChildRepositoryStub();
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, childRepository);
        String createdSubscriptionId = subscriptionService.create(testSubscriptionDTO);

        assertThat(createdSubscriptionId).isNotNull();
        assertThat(createdSubscriptionId).isEqualTo(testSubscriptionDTO.getId());
    }

    @Test
    void update_subscriptionIdAndDTO_updatedSubscription() {
        SubscriptionDTO updatedSubscriptionDTO = testSubscriptionDTO;
        String id = updatedSubscriptionDTO.getId();

        SubscriptionRepositorySpy subscriptionRepositorySpy = new SubscriptionRepositorySpy();
        childRepository = new ChildRepositoryStub();
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionRepositorySpy, childRepository);

        subscriptionService.update(id, updatedSubscriptionDTO);

        assertEquals(1, subscriptionRepositorySpy.getInvocationsOf_findById());
        assertEquals(1, subscriptionRepositorySpy.getInvocationsOf_save());
    }

    @Test
    void update_NonExistentSubscription_throwsNotFoundException() {
        SubscriptionRepository subscriptionRepository = new SubscriptionRepositoryMock();
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, childRepository);

        assertThrows(NotFoundException.class, () -> subscriptionService.update("fakeId", testSubscriptionDTO));
    }

    @Test
    void delete_subscriptionId_deletedSubscription() {
        SubscriptionRepositorySpy subscriptionRepositorySpy = new SubscriptionRepositorySpy();
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionRepositorySpy, childRepository);

        subscriptionService.delete(testSubscription.getId());

        assertEquals(1, subscriptionRepositorySpy.getInvocationsOf_delete());
    }
}
