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

import javax.persistence.EntityManager;

import rva.simpleSpringBoot.model.Cat;
import rva.simpleSpringBoot.model.Person;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

@Transactional
public abstract class AbstractSampleTest {

    @Autowired
    protected EntityManager em;

    @Before
    public void init() {

// Get Expected Person Count
        LoadProperies loadProperies = new LoadProperies("application.properties");
        long expectedPersonCount = Long.parseLong(loadProperies.getProperties().getProperty("test.person.count"));
        long expectedCatCount = Long.parseLong(loadProperies.getProperties().getProperty("test.cat.count"));

        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<Cat> cats = new ArrayList<>();
        HashSet<Integer> kittens = new HashSet<>();
        String personName;
        String catName;
        long i;
        int age;
        int owner;
        int parentCount;
        int kittenCount;
        int rand;
        int parentNumber;
        final Random random = new Random();

// Create Persons
        for (i = 0; i < expectedPersonCount; i++) {
            personName = "Person" + i;
            Person person = new Person(personName);
            em.persist(person);
            persons.add(person);
        }
// Create Cats
        Cat cat;
        for (i = 0; i < expectedCatCount; i++) {
            catName = "Cat" + i;
            age = random.nextInt(15) + 1;
            rand = random.nextInt(100);
            if (rand < 80) { // set Owner for 80% cats only
                owner = random.nextInt((int) expectedPersonCount);
                if (owner == expectedPersonCount) {
                    owner = 0;
                }
                cat = new Cat(catName, age, persons.get(owner));
            } else {
                cat = new Cat(catName, age, null);
            }
            em.persist(cat);
            cats.add(cat);
        }

// Set Relations
        parentCount = random.nextInt((int) (expectedCatCount / 2));
        if (parentCount == 0) {
            parentCount = (int) (expectedCatCount / 2);
        }
// Define kittens
        kittenCount = (int) (expectedCatCount - parentCount);
        for (i = parentCount + 1; i < expectedCatCount; i++) {
            parentNumber = random.nextInt(parentCount);
            rand = random.nextInt(100);
            if (rand > 50) {
                cats.get((int) i).setFather(cats.get(parentNumber));
                cats.get(parentNumber).getKittens().add(cats.get((int) i));
            } else {
                cats.get((int) i).setMother(cats.get(parentNumber));
                cats.get(parentNumber).getKittens().add(cats.get((int) i));
            }
            kittens.add((int) i);

// /RVA
        }
    }
}
