package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealTimeRemoteDataSource {
    private val database = FirebaseDatabase.getInstance()
    private val dollarRef = database.getReference("dollar")

    fun getDollarUpdates(): Flow<DollarModel> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dollarOfficialCompra = snapshot.child("dollarOfficialCompra").getValue(String::class.java) ?: "0.00"
                val dollarOfficialVenta = snapshot.child("dollarOfficialVenta").getValue(String::class.java) ?: "0.00"
                val dollarParallelCompra = snapshot.child("dollarParallelCompra").getValue(String::class.java) ?: "0.00"
                val dollarParallelVenta = snapshot.child("dollarParallelVenta").getValue(String::class.java) ?: "0.00"

                val dollar = DollarModel(
                    dollarOfficialCompra = dollarOfficialCompra,
                    dollarOfficialVenta = dollarOfficialVenta,
                    dollarParallelCompra = dollarParallelCompra,
                    dollarParallelVenta = dollarParallelVenta,
                    timestamp = System.currentTimeMillis()
                )
                trySend(dollar)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        dollarRef.addValueEventListener(listener)
        awaitClose { dollarRef.removeEventListener(listener) }
    }
}
