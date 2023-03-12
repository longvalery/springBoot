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

import rva.simpleSpringBoot.LoadProperies;
import rva.simpleSpringBoot.model.Cat;
import rva.simpleSpringBoot.model.Person;
import rva.simpleSpringBoot.view.CatSimpleView;
import rva.simpleSpringBoot.view.CatWithOwnerView;
import rva.simpleSpringBoot.view.PersonSimpleView;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Transactional
public abstract class AbstractSampleTest {

    @Autowired
    protected EntityManager em;

    @Before
    public void init() {

// Get Expected Person Count
        LoadProperies loadProperies = new LoadProperies("application.properties");
//        System.out.println("-=-=-  " +loadProperies.getProperties().getProperty("test.person.count").toString());
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
        for (i=0; i < expectedPersonCount; i++) {
            personName = "Person" + i;
            Person person = new Person(personName);
            em.persist(person);
            persons.add(person);
                                                }
// Create Cats
        for (i=0; i < expectedCatCount; i++) {
            catName = "Cat" + i;
            age = random.nextInt(15);
            owner = random.nextInt((int) expectedPersonCount);
            if (owner == expectedPersonCount) {
                owner = 0;
                                              }
            Cat cat = new Cat(catName,age,persons.get(owner));
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
        for (i=parentCount+1; i < expectedCatCount; i++) {
            parentNumber = random.nextInt(parentCount);
            rand = random.nextInt(100);
            if (rand > 50) {
                cats.get((int) i).setFather(cats.get(parentNumber));
                cats.get(parentNumber).getKittens().add(cats.get((int) i));
                           }
                else       {
                cats.get((int) i).setMother(cats.get(parentNumber));
                cats.get(parentNumber).getKittens().add(cats.get((int) i));
                            }
            kittens.add((int) i);

                                                         }


 /* Old code is :
        Person p1 = new Person("P1");
        Person p2 = new Person("P2");
        Person p3 = new Person("P3");
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);

        Cat c1 = new Cat("C1", 1, persons.get(0));
        Cat c2 = new Cat("C2", 2, persons.get(0));
        Cat c3 = new Cat("C3", 4, persons.get(0));

        Cat c4 = new Cat("C4", 6, persons.get(1));

        Cat c5 = new Cat("C5", 8, null);
        Cat c6 = new Cat("C6", 7, null);

        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.persist(c5);
        em.persist(c6);

        c1.setMother(c3);
        c3.getKittens().add(c1);

        c1.setFather(c5);
        c5.getKittens().add(c1);

        c2.setMother(c3);
        c3.getKittens().add(c2);

        c2.setFather(c6);
        c6.getKittens().add(c2);

        c4.setFather(c6);
        c6.getKittens().add(c4);

  */
// RVA
        /*
        HashSet<Cat> kittens1 = new HashSet<>();
        kittens1.add(c3);
        kittens1.add(c1);
        persons.get(0).setKittens(kittens1);

        HashSet<Cat> kittens2 = new HashSet<>();
        kittens1.add(c5);
        kittens1.add(c2);
        persons.get(1).setKittens(kittens2);

        HashSet<Cat> kittens3 = new HashSet<>();
        kittens1.add(c6);
        kittens1.add(c6);
        persons.get(2).setKittens(kittens3);
        */
     //   System.out.println("===============init AbstractSampleTest======================");
// /RVA


    }
}
