package br.com.castellan.whitelabel.data

import android.net.Uri
import br.com.castellan.whitelabel.BuildConfig.FIREBASE_FLAVOR_COLLECTION
import br.com.castellan.whitelabel.domain.model.Product
import br.com.castellan.whitelabel.util.COLLECTION_PRODUCTS
import br.com.castellan.whitelabel.util.COLLECTION_ROOT
import br.com.castellan.whitelabel.util.STORAGE_IMAGES
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseProductDataSource @Inject constructor(
    firebaseStorage: FirebaseStorage,
    firebaseFirestore: FirebaseFirestore,
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


            productsReference.get().addOnFailureListener { exception ->
                it.resumeWith(Result.failure(exception))
            }
        }
    }

    override suspend fun uploadProductImage(imageUri: Uri): String {
        return suspendCoroutine {
            val key = UUID.randomUUID()

            val childReference = storageReference.child(
                "$STORAGE_IMAGES/$FIREBASE_FLAVOR_COLLECTION/$key"
            )

            childReference.putFile(imageUri)
                .addOnSuccessListener { task ->
                    task.storage.downloadUrl.addOnSuccessListener { uri ->
                        val path = uri.toString()
                        it.resumeWith(Result.success(path))
                    }
                }
                .addOnFailureListener { exception ->
                    it.resumeWith(Result.failure(exception))
                }
        }
    }

    override suspend fun createProduct(product: Product): Product {
        return suspendCoroutine {
            documentReference
                .collection(COLLECTION_PRODUCTS)
                .document(System.currentTimeMillis().toString())
                .set(product)
                .addOnSuccessListener { _ ->
                    it.resumeWith(Result.success(product))
                }
                .addOnFailureListener { exception ->
                    it.resumeWith(Result.failure(exception))
                }
        }
    }
}