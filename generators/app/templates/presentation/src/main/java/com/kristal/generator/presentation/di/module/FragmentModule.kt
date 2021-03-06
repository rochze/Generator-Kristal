package <%= appPackage %>.presentation.di.module

import <%= appPackage %>.presentation.di.module.base.EmptyModule
import <%= appPackage %>.presentation.example.room.view.fragment.RoomFragment
import <%= appPackage %>.presentation.example.database.view.fragment.ExampleDatabaseFragment
import <%= appPackage %>.presentation.example.database.view.fragment.ExampleFragment
import <%= appPackage %>.presentation.example.recyclerview.view.fragment.RecyclerViewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Dwi_Ari on 10/14/17.
 */

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector(modules = arrayOf(EmptyModule::class))
    internal abstract fun bindLearning(): ExampleFragment

    @ContributesAndroidInjector(modules = arrayOf(EmptyModule::class))
    internal abstract fun bindED(): ExampleDatabaseFragment

    @ContributesAndroidInjector(modules = arrayOf(EmptyModule::class))
    internal abstract fun bindRV(): RecyclerViewFragment

    @ContributesAndroidInjector(modules = arrayOf(EmptyModule::class))
    internal abstract fun bindR(): RoomFragment
}
