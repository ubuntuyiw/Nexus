package com.ubuntuyouiwe.nexus.data.source.local.proto.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import java.io.File

fun <T> Context.createProtoDataStore(
    fileName: String,
    serializer: Serializer<T>
): DataStore<T> {
    return DataStoreFactory.create(
        serializer = serializer,
        produceFile = { File(this.filesDir, fileName) }
    )
}