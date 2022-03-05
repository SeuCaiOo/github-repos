package br.com.seucaio.githubreposkotlin.core.ext

import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

fun Retrofit.Builder.addCallAdapterFactories(factories: List<CallAdapter.Factory>) = apply {
    factories.forEach(::addCallAdapterFactory)
}

fun Retrofit.Builder.addConverterFactories(factories: List<Converter.Factory>) = apply {
    factories.forEach(::addConverterFactory)
}