package com.parentapp.archUnit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

@AnalyzeClasses(packages = "com.parentapp", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {
    @ArchTest
    public static final ArchRule controllersCannotBeAccessedByAnyOtherLayer = classes().that().areAnnotatedWith(RestController.class)
            .should().onlyBeAccessed().byClassesThat().areAnnotatedWith(RestController.class)
            .as("Controllers cannot be accessed by any other layer.");
    @ArchTest
    public static final ArchRule servicesCanBeAccessedOnlyByControllersAndOtherServices = classes()
            .that().areAnnotatedWith(Service.class)
            .should().onlyBeAccessed().byClassesThat().areAnnotatedWith(RestController.class)
            .orShould().onlyBeAccessed().byClassesThat().areAnnotatedWith(Service.class)
            .orShould().dependOnClassesThat().haveSimpleNameEndingWith("Repository")
            .as("Services can be accessed only by controllers and other services.");
    @ArchTest
    public static final ArchRule entitiesCanBeAccessedOnlyByRepositoriesServicesAndConfigurationClasses = classes()
            .that().areAnnotatedWith(Entity.class)
            .should().onlyBeAccessed().byClassesThat().haveSimpleNameEndingWith("Repository")
            .orShould().onlyBeAccessed().byClassesThat().areAnnotatedWith(Service.class)
            .orShould().onlyBeAccessed().byClassesThat().haveSimpleNameEndingWith("Config")
            .orShould().dependOnClassesThat().resideInAPackage( "java..")
            .as("Entities can be accessed only by repositories, services and configuration classes.");
    @ArchTest
    public static final ArchRule repositoriesCanBeAccessedOnlyByServicesAndConfigurationClasses = classes()
            .that().haveSimpleNameEndingWith("Repository")
            .should().onlyBeAccessed().byClassesThat().areAnnotatedWith(Service.class)
            .orShould().onlyBeAccessed().byClassesThat().haveSimpleNameEndingWith("Config")
            .as("Repositories can be accessed only by services and configuration classes.");
    @ArchTest
    public static final ArchRule classesDoNotUseFieldInjection = noFields()
            .should().beAnnotatedWith(Autowired.class)
            .as("Classes don’t use field injection.");
    @ArchTest
    public static final ArchRule noClassShouldUseSystemOutMethods = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS
            .because("No class should use System.out methods");

}
