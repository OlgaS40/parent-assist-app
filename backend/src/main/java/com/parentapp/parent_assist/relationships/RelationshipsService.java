package com.parentapp.parent_assist.relationships;

import com.parentapp.parent_assist.children.Children;
import com.parentapp.parent_assist.children.ChildrenRepository;
import com.parentapp.parent_assist.parents.Parents;
import com.parentapp.parent_assist.parents.ParentsRepository;
import com.parentapp.parent_assist.plan.Plan;
import com.parentapp.parent_assist.plan.PlanRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RelationshipsService {

    private final RelationshipsRepository relationshipsRepository;
    private final ParentsRepository parentsRepository;
    private final ChildrenRepository childrenRepository;
    private final PlanRepository planRepository;

    public RelationshipsService(final RelationshipsRepository relationshipsRepository,
            final ParentsRepository parentsRepository, final ChildrenRepository childrenRepository,
            final PlanRepository planRepository) {
        this.relationshipsRepository = relationshipsRepository;
        this.parentsRepository = parentsRepository;
        this.childrenRepository = childrenRepository;
        this.planRepository = planRepository;
    }

    public List<RelationshipsDTO> findAll() {
        final List<Relationships> relationshipss = relationshipsRepository.findAll(Sort.by("id"));
        return relationshipss.stream()
                .map((relationships) -> mapToDTO(relationships, new RelationshipsDTO()))
                .collect(Collectors.toList());
    }

    public RelationshipsDTO get(final String id) {
        return relationshipsRepository.findById(id)
                .map(relationships -> mapToDTO(relationships, new RelationshipsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final RelationshipsDTO relationshipsDTO) {
        final Relationships relationships = new Relationships();
        mapToEntity(relationshipsDTO, relationships);
        relationships.setId(relationshipsDTO.getId());
        return relationshipsRepository.save(relationships).getId();
    }

    public void update(final String id, final RelationshipsDTO relationshipsDTO) {
        final Relationships relationships = relationshipsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(relationshipsDTO, relationships);
        relationshipsRepository.save(relationships);
    }

    public void delete(final String id) {
        relationshipsRepository.deleteById(id);
    }

    private RelationshipsDTO mapToDTO(final Relationships relationships,
            final RelationshipsDTO relationshipsDTO) {
        relationshipsDTO.setId(relationships.getId());
        relationshipsDTO.setRelationship(relationships.getRelationship());
        relationshipsDTO.setParentId(relationships.getParentId() == null ? null : relationships.getParentId().getId());
        relationshipsDTO.setChildId(relationships.getChildId() == null ? null : relationships.getChildId().getId());
        relationshipsDTO.setPlanId(relationships.getPlanId() == null ? null : relationships.getPlanId().getId());
        return relationshipsDTO;
    }

    private Relationships mapToEntity(final RelationshipsDTO relationshipsDTO,
            final Relationships relationships) {
        relationships.setRelationship(relationshipsDTO.getRelationship());
        final Parents parentId = relationshipsDTO.getParentId() == null ? null : parentsRepository.findById(relationshipsDTO.getParentId())
                .orElseThrow(() -> new NotFoundException("parentId not found"));
        relationships.setParentId(parentId);
        final Children childId = relationshipsDTO.getChildId() == null ? null : childrenRepository.findById(relationshipsDTO.getChildId())
                .orElseThrow(() -> new NotFoundException("childId not found"));
        relationships.setChildId(childId);
        final Plan planId = relationshipsDTO.getPlanId() == null ? null : planRepository.findById(relationshipsDTO.getPlanId())
                .orElseThrow(() -> new NotFoundException("planId not found"));
        relationships.setPlanId(planId);
        return relationships;
    }

    public boolean idExists(final String id) {
        return relationshipsRepository.existsByIdIgnoreCase(id);
    }

}
