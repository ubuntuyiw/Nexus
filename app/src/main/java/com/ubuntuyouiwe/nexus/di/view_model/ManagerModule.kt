package com.ubuntuyouiwe.nexus.di.view_model

import com.android.billingclient.api.BillingClient
import com.ubuntuyouiwe.nexus.data.manager.BillingManagerImpl
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ManagerModule {

    @Provides
    @ViewModelScoped
    fun provideBillingClientManager(
        billingClient: BillingClient.Builder,
        firebaseDataSource: FirebaseDataSource,
        json: Json,
    ): BillingManager =
        BillingManagerImpl(billingClient, firebaseDataSource, json)
}