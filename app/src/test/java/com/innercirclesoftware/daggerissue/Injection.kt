package com.innercirclesoftware.daggerissue

import dagger.Component
import dagger.Module
import dagger.Provides
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Singleton
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApp

@PerApp
@Component(
    modules = [TestModule::class]
)
interface TestComponent {

    fun getString(): String

}

@Module
abstract class TestModule {

    @Module
    companion object {

        @Provides
        @PerApp
        @JvmStatic
        fun providesString() = "test string"

    }

}

class InjectionTest {

    @Test
    fun `test injection`() {
        Assert.assertEquals(component.getString(), "test string")
    }

    companion object {

        lateinit var component: TestComponent

        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            component = DaggerTestComponent.create();
        }
    }
}