package de.jensomato.sample

import de.jensomato.sample.data.JsonPlaceholderService
import de.jensomato.sample.presentation.*
import de.jensomato.sample.respository.*
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

val appModule = module {
    single<JsonPlaceholderService> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create<JsonPlaceholderService>(JsonPlaceholderService::class.java)

    }

    single<PostsRepository> { PostsRepositroyImpl(get()) }

    single<CommentsRepository> { CommentsRepositroyImpl(get()) }

    single<FavoritesRepository> { FavoritesRepositroyImpl() }

    single<CoroutineContextProvider> { object : CoroutineContextProvider {
        override val main: CoroutineContext
            get() = Dispatchers.Main
        override val io: CoroutineContext
            get() = Dispatchers.IO
    } }

    factory<PostsContract.Presenter> { (view: PostsContract.View) -> PostsPresenter(get(), get(), get(), view) }

    factory<CommentsContract.Presenter> { (view: CommentsContract.View) -> CommentsPresenter(get(), get(), get(), get(), view) }

}