package br.com.castellan.whitelabel.data

import android.net.Uri
import br.com.castellan.whitelabel.BuildConfig.FIREBASE_FLAVOR_COLLECTION
import br.com.castellan.whitelabel.domain.model.Product
import br.com.castellan.whitelabel.util.COLLECTION_PRODUCTS
import br.com.castellan.whitelabel.util.COLLECTION_ROOT
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlin.coroutines.suspendCoroutine

class FirebaseProductDataSource(
    val firebaseStorage: FirebaseStorage,
    val firebaseFirestore: FirebaseFirestore,
) : ProductDataSource {

    private val documentReference =
        firebaseFirestore.document("$COLLECTION_ROOT/$FIREBASE_FLAVOR_COLLECTION/")

    private val storageReference = firebaseStorage.reference

    override suspend fun getProducts(): List<Product> {

        return suspendCoroutine {


            val productsReference = documentReference.collection(COLLECTION_PRODUCTS)
            productsReference.get().addOnSuccessListener { documents ->
                val products = mutableListOf<Product>()

                for (document in documents) {
                    document.toObject(Product::class.java).run { products.add(this) }
                }

                it.resumeWith(Result.success(products))
            }


            productsReference.get().addOnFailureListener { execption ->
                it.resumeWith(Result.failure(execption))
            }
        }
    }

    override suspend fun uploadProductImage(imageUri: Uri): String {
        TODO("Not yet implemented")
    }

    override suspend fun createProduct(product: Product): Product {
        TODO("Not yet implemented")
    }
}