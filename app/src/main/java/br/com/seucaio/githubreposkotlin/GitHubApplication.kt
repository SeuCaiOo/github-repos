package br.com.seucaio.githubreposkotlin

import android.app.Application
import br.com.seucaio.githubreposkotlin.di.DataModule
import br.com.seucaio.githubreposkotlin.di.PresentationModule
import br.com.seucaio.githubreposkotlin.di.RemoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            setupKoinLogger()
            androidContext(this@GitHubApplication)
            loadAllModules()
        }
    }

    private fun KoinApplication.setupKoinLogger() {
        if (BuildConfig.DEBUG) {
            logger(object : Logger(Level.ERROR) {
                override fun log(level: Level, msg: MESSAGE) {
                    when (level) {
                        Level.DEBUG -> Timber.d(msg)
                        Level.INFO -> Timber.i(msg)
                        Level.ERROR -> Timber.e(msg)
                    }
                }
            })
        } else {
            logger(EmptyLogger())
        }
    }

    private fun loadAllModules() {
        RemoteModule.load()
        DataModule.load()
        PresentationModule.load()
    }
}