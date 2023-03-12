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
import java.util.concurrent.atomic.AtomicLong;

import rva.simpleSpringBoot.LoadProperies;
import rva.simpleSpringBoot.model.Cat;
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
//        System.out.println("-=-=-  " +loadProperies.getProperties().getProperty("test.person.count").toString());
        long expectedPersonCount = Long.parseLong(loadProperies.getProperties().getProperty("test.person.count"));
// Test
        final Set<Cat>[] cats = new Set[0];

        final Iterable<PersonSimpleView> listPersons = personSimpleViewRepository.findAll();
        final AtomicLong[] count = {new AtomicLong()};
        count[0].set(0);
        listPersons.forEach(person -> {
            count[0].getAndIncrement();
            System.out.println("Person name is  " + person.getName());
      //      cats[0] = person.getKittens();
      //      cats[0].forEach(cat -> {
      //          System.out.println("  has cat called  " + cat.getName());
      //                          });
        });

        Assert.assertEquals(expectedPersonCount, count[0].longValue());
//        System.out.println("-=-=-  " + count[0].toString());
                                 }
// /RVA
    @Test
    public void sampleTest() {
// Get Expected Person Count
        LoadProperies loadProperies = new LoadProperies("application.properties");
//        System.out.println("-=-=-  " +loadProperies.getProperties().getProperty("test.person.count").toString());
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

    @Configuration
    @ComponentScan("rva.simpleSpringBoot")
    @EnableEntityViews(basePackages = { "rva.simpleSpringBoot.view"})
    @EnableBlazeRepositories(
            basePackages = "rva.simpleSpringBoot.repository")
    static class TestConfig {
    }
}
