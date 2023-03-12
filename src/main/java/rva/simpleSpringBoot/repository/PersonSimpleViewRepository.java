package rva.simpleSpringBoot.repository;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rva.simpleSpringBoot.view.PersonSimpleView;

@Repository
@Transactional(readOnly = true)
public interface PersonSimpleViewRepository extends EntityViewRepository<PersonSimpleView, Long>  {
}
