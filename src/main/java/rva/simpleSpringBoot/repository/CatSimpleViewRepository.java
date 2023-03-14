/*
 * Copyright 2014 - 2022 Blazebit.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rva.simpleSpringBoot.repository;

import com.blazebit.persistence.view.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import rva.simpleSpringBoot.model.Cat;
import rva.simpleSpringBoot.model.Person;
import rva.simpleSpringBoot.view.*;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CatSimpleViewRepository extends EntityViewRepository<CatSimpleView, Long> {
// RVA
 //   Cat findById(long id);
    List<Cat> findAllByOwner(Optional<Person> owner);


    @Query(value = "select cat.name as catName, cat.age as catAge,  COALESCE(person.name,'No owner') as personName " +
            "from cat cat " +
            "left join person person on cat.owner_id=person.id ",
            nativeQuery = true)
    List<CatWithPersonView> findAllCatsWithOwner();

// /RVA
}
