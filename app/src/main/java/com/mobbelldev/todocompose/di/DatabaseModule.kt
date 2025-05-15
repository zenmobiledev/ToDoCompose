package com.mobbelldev.todocompose.di

import android.content.Context
import androidx.room.Room
import com.mobbelldev.todocompose.data.ToDoDatabase
import com.mobbelldev.todocompose.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context = context,
        klass = ToDoDatabase::class.java,
        name = DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: ToDoDatabase) = database.toDoDao()
}