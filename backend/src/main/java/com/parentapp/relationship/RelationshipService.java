package com.parentapp.relationship;

import com.parentapp.child.Child;
import com.parentapp.child.ChildRepository;
import com.parentapp.parent.Parent;
import com.parentapp.parent.ParentRepository;
import com.parentapp.plan.Plan;
import com.parentapp.plan.PlanRepository;
import com.parentapp.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final PlanRepository planRepository;

    public RelationshipService(final RelationshipRepository relationshipRepository,
                               final ParentRepository parentRepository, final ChildRepository childRepository,
                               final PlanRepository planRepository) {
        this.relationshipRepository = relationshipRepository;
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
        this.planRepository = planRepository;
    }

    public List<RelationshipDTO> findAll() {
        final List<Relationship> relationships = relationshipRepository.findAll(Sort.by("id"));
        return relationships.stream()
                .map((relationship) -> mapToDTO(relationship, new RelationshipDTO()))
                .collect(Collectors.toList());
    }

    public RelationshipDTO get(final String id) {
        return relationshipRepository.findById(id)
                .map(relationships -> mapToDTO(relationships, new RelationshipDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final RelationshipDTO relationshipDTO) {
        final Relationship relationship = new Relationship();
        mapToEntity(relationshipDTO, relationship);
        relationship.setId(relationshipDTO.getId());
        return relationshipRepository.save(relationship).getId();
    }

    public void update(final String id, final RelationshipDTO relationshipDTO) {
        final Relationship relationship = relationshipRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(relationshipDTO, relationship);
        relationshipRepository.save(relationship);
    }

    public void delete(final String id) {
        relationshipRepository.deleteById(id);
    }

    private RelationshipDTO mapToDTO(final Relationship relationship,
                                     final RelationshipDTO relationshipDTO) {
        relationshipDTO.setId(relationship.getId());
        relationshipDTO.setRelationshipEnum(relationship.getRelationshipEnum());
        relationshipDTO.setParentId(relationship.getParent() == null ? null : relationship.getParent().getId());
        relationshipDTO.setChildId(relationship.getChild() == null ? null : relationship.getChild().getId());
        relationshipDTO.setPlanId(relationship.getPlan() == null ? null : relationship.getPlan().getId());
        return relationshipDTO;
    }

    private Relationship mapToEntity(final RelationshipDTO relationshipDTO,
                                     final Relationship relationship) {
        relationship.setRelationshipEnum(relationshipDTO.getRelationshipEnum());
        final Parent parentId = relationshipDTO.getParentId() == null ? null : parentRepository.findById(relationshipDTO.getParentId())
                .orElseThrow(() -> new NotFoundException("parentId not found"));
        relationship.setParent(parentId);
        final Child childId = relationshipDTO.getChildId() == null ? null : childRepository.findById(relationshipDTO.getChildId())
                .orElseThrow(() -> new NotFoundException("childId not found"));
        relationship.setChild(childId);
        final Plan planId = relationshipDTO.getPlanId() == null ? null : planRepository.findById(relationshipDTO.getPlanId())
                .orElseThrow(() -> new NotFoundException("planId not found"));
        relationship.setPlan(planId);
        return relationship;
    }

    public boolean idExists(final String id) {
        return relationshipRepository.existsByIdIgnoreCase(id);
    }

}
