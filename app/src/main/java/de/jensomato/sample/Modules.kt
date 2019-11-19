package de.jensomato.sample

import de.jensomato.sample.data.JsonPlaceholderService
import de.jensomato.sample.respository.*
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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


}