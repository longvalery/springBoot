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

package rva.simpleSpringBoot.sample;

import java.util.*;


import rva.simpleSpringBoot.LoadProperies;
import rva.simpleSpringBoot.model.Cat;
import rva.simpleSpringBoot.model.Person;
import rva.simpleSpringBoot.repository.PersonSimpleViewRepository;
import rva.simpleSpringBoot.view.CatSimpleView;
import rva.simpleSpringBoot.repository.CatSimpleViewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.*;
import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spring.data.repository.config.EnableBlazeRepositories;
//  import rva.simpleSpringBoot.view.CatWithOwnerView;
import rva.simpleSpringBoot.view.CatWithMotherAndFatherView;
import rva.simpleSpringBoot.view.CatWithPersonView;
import rva.simpleSpringBoot.view.PersonSimpleView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SampleTest.TestConfig.class)
public class SampleTest extends AbstractSampleTest {

    @Autowired
    private CatSimpleViewRepository catSimpleViewRepository;

// RVA
    @Autowired
    private PersonSimpleViewRepository personSimpleViewRepository;
    @Test
    public void sampleTestRVA() {
// Check Persons
// Get Expected Person Count
        LoadProperies loadProperies = new LoadProperies("application.properties");
        long expectedPersonCount = Long.parseLong(loadProperies.getProperties().getProperty("test.person.count"));
// Test
        final Iterable<PersonSimpleView> listPersons = personSimpleViewRepository.findAll();
        long count = 0;
        for(PersonSimpleView person: listPersons) {
            count++;
            long id = person.getId();
            Optional<Person> p = personSimpleViewRepository.findById(id);
            List<Cat> cats = catSimpleViewRepository.findAllByOwner(p);
            System.out.println("Person name is  " + person.getName());
            if (cats.size() == 0) {System.out.println("  has NO cats! ");}
            else {
                cats.forEach(cat -> System.out.println("  has cat called  " + cat.getName()) );
                 }
                                         }

        Assert.assertEquals(expectedPersonCount, count);
                                 }
// /RVA
    @Test
    public void sampleTest() {
// Get Expected Person Count
        LoadProperies loadProperies = new LoadProperies("application.properties");
        long expectedCatCount = Long.parseLong(loadProperies.getProperties().getProperty("test.cat.count"));
// Test
        final Iterable<CatSimpleView> listIterable = catSimpleViewRepository.findAll();
        final List<CatSimpleView> list = new ArrayList<>();
        listIterable.forEach(view -> {
            list.add(view);
// RVA
            System.out.println("Cat name is " + view.getName());
// /RVA
                                     });
        Assert.assertEquals(expectedCatCount, list.size());
    }

// RVA
    @Test
    public void sampleTestRVACatsWithOwnerByQuery() {
// Get Expected Person Count
        LoadProperies loadProperies = new LoadProperies("application.properties");
        long expectedCatCount = Long.parseLong(loadProperies.getProperties().getProperty("test.cat.count"));
// Test
        final Iterable<CatWithPersonView> listIterable = catSimpleViewRepository.findAllCatsWithOwner();
        long count = 0;
        for(CatWithPersonView cat : listIterable ) {
            count ++;
            System.out.println("Cat name is " + cat.getCatName() + " Age:" + cat.getCatAge() + " belongs  Owner:" + cat.getPersonName());
                                                }
        Assert.assertEquals(expectedCatCount, count );
    }

    @Test
    public void sampleTestRVACatsWithMotherAndFatherByQuery() {
// Get Expected Person Count
        LoadProperies loadProperies = new LoadProperies("application.properties");
        long expectedCatCount = Long.parseLong(loadProperies.getProperties().getProperty("test.cat.count"));
// Test
        final Iterable<CatWithMotherAndFatherView> listIterable = catSimpleViewRepository.findAllCatsWithMotherAndFather();
        long count = 0;
        for(CatWithMotherAndFatherView cat : listIterable ) {
            count ++;
            System.out.println("Cat name is " + cat.getCatName() + " Age:" + cat.getCatAge()
                    + " Mother:" + cat.getMother() + " Father:" + cat.getFather());
        }
        Assert.assertEquals(expectedCatCount, count );
    }


// /RVA
    @Configuration
    @ComponentScan("rva.simpleSpringBoot")
    @EnableEntityViews(basePackages = { "rva.simpleSpringBoot.view"})
    @EnableBlazeRepositories(
            basePackages = "rva.simpleSpringBoot.repository")
    static class TestConfig {
                            }
}
