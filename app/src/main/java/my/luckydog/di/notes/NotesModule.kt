package my.luckydog.di.notes

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.boundaries.notes.Note
import my.luckydog.boundaries.notes.NotesRepository
import my.luckydog.common.Mapper
import my.luckydog.data.app.db.LuckyDogDatabase
import my.luckydog.data.app.db.note.DbNote
import my.luckydog.data.app.db.note.NoteDao
import my.luckydog.data.notes.DbNoteMapper
import my.luckydog.data.notes.NoteMapper
import my.luckydog.data.notes.NotesRepositoryImpl
import my.luckydog.di.app.AppModule.Companion.APP_CONTEXT
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.domain.core.translate.DetectCase
import my.luckydog.domain.core.translate.DetectCaseImpl
import my.luckydog.domain.core.translate.TranslateCase
import my.luckydog.domain.core.translate.TranslateCaseImpl
import my.luckydog.domain.notes.*

import my.luckydog.interactors.notes.ItemNote
import my.luckydog.interactors.notes.NotesInteractor
import my.luckydog.presentation.fragments.notes.dialogs.NotesDialogs
import my.luckydog.presentation.fragments.notes.dialogs.NotesDialogsImpl
import javax.inject.Named

@Module
abstract class NotesModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @FragmentScope
        fun provideDao(@Named(APP_CONTEXT) context: Context): NoteDao {
            println("!!!dagger: provideDao")
            return LuckyDogDatabase.getDatabase(context).noteDao()
        }
    }

    @Binds
    @FragmentScope
    abstract fun provideDialogs(dialogs: NotesDialogsImpl): NotesDialogs

    @Binds
    @FragmentScope
    abstract fun provideInteractor(interactor: NotesInteractorImpl): NotesInteractor

    @Binds
    @FragmentScope
    abstract fun provideAddCase(case: AddCaseImpl): AddCase

    @Binds
    @FragmentScope
    abstract fun provideDeleteCase(case: DeleteCaseImpl): DeleteCase

    @Binds
    @FragmentScope
    abstract fun provideUpdateCase(case: UpdateCaseImpl): UpdateCase

    @Binds
    @FragmentScope
    abstract fun provideGetAllCase(case: GetAllCaseImpl): GetAllCase

    @Binds
    @FragmentScope
    abstract fun provideDetectCase(case: DetectCaseImpl): DetectCase

    @Binds
    @FragmentScope
    abstract fun provideTranslateCase(case: TranslateCaseImpl): TranslateCase

    @Binds
    @FragmentScope
    abstract fun provideRepository(repository: NotesRepositoryImpl): NotesRepository

    @Binds
    @FragmentScope
    abstract fun provideNoteMapper(mapper: NoteMapper): Mapper<DbNote, Note>

    @Binds
    @FragmentScope
    abstract fun provideItemNoteMapper(mapper: ItemNoteMapper): Mapper<Note, ItemNote>

    @Binds
    @FragmentScope
    abstract fun provideDbNoteMapper(mapper: DbNoteMapper): Mapper<Note, DbNote>

    @Binds
    @FragmentScope
    abstract fun provideToNoteMapper(mapper: my.luckydog.domain.notes.NoteMapper): Mapper<ItemNote, Note>
}