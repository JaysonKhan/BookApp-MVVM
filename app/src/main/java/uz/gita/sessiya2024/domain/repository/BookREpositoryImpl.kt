package uz.gita.sessiya2024.domain.repository

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import uz.gita.sessiya2024.data.model.BookData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import uz.gita.sessiya2024.data.local.dao.BookDao
import uz.gita.sessiya2024.data.model.MessageData
import uz.gita.sessiya2024.data.model.ProfilData
import uz.gita.sessiya2024.data.model.TypeData
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.Exception


class BookREpositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val storageReference: StorageReference,
    private val dao: BookDao
):BookRepository {
    val local = dao.getBooks()

    override suspend fun getAllBooks(): Result<List<TypeData>>  {
        try {
            val querySnapshot = fireStore.collection("universities").get().await()

            if(querySnapshot.isEmpty) return Result.failure(Exception("Empty documents"))

            val result = arrayListOf<TypeData>()

            querySnapshot.documents.forEach { snapshot ->
                val id = snapshot["id"] as Long
                val title = snapshot["title"] as String
                val icon = snapshot["icon"] as String
                val isActiveUnv = snapshot["isActive"] as Boolean
                if (!isActiveUnv){
                    return@forEach
                }
                val list = arrayListOf<BookData>()

                snapshot.reference.collection("bazalar").get()
                    .addOnSuccessListener { querySnapshot ->
                        querySnapshot.documents.forEach { book ->
                            val newBook = BookData(
                                book.get("id") as Long,
                                book.get("name") as String,
                                book.get("pdfurl") as String,
                                book.get("imageurl") as String,
                                book.get("kurs") as Long,
                                book.get("year") as Long,
                                book.get("owner") as String,
                                book.get("date") as String,
                                book.get("unvshort") as String,
                                book.get("reference") as String,
                                book.get("isActive") as Boolean,
                                book.get("description") as String
                            )
                            if (newBook.isActive) {
                                list.add(newBook)
                                val ent = newBook.toEntity(title, icon)
                                if (dao.getBook(ent.id) == null) {
                                    if (isBookNew(newBook))
                                        ent.isnewbookread = 1
                                    dao.addBook(ent)
                                } else {
                                    ent.isnewbookread = dao.getBook(ent.id).isnewbookread
                                    dao.updateBook(ent)
                                }
                            }
                        }
                    }.await()
                result.add(TypeData(id, icon, title, isActiveUnv, list))
            }
            return Result.success(result)

        }catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>> = callbackFlow {
        val newREference = "${data.reference}.pdf"
        if (File(context.filesDir, "$newREference.pdf").exists()){
            trySend(Result.success(data))
        }else{
            storageReference.child( "documents/$newREference")
                .getFile(File(context.filesDir, newREference))
                .addOnSuccessListener {
                    trySend(Result.success(data))
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }
        }
        awaitClose()
    }
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun isBookNew(book: BookData):Boolean{
        val curentDay = Calendar.getInstance().time.toString("dd").toInt()
        val curentMonth = Calendar.getInstance().time.toString("MM").toInt()

                val day = book.date.subSequence(0,2).toString().toInt()
                val month = book.date.subSequence(3,5).toString().toInt()
                if ((day-curentDay)<=3 && (month-curentMonth)==0){
                 return true
        }
        return false
    }
    override fun getNewBooks(): List<BookData> {
        val curentDay = Calendar.getInstance().time.toString("dd").toInt()
        val curentMonth = Calendar.getInstance().time.toString("MM").toInt()

        val result = arrayListOf<BookData>()
        val lst = getFullBookList()
        if (!lst.isNullOrEmpty()) {
            lst.forEach { book ->
                    val day = book.date.subSequence(0,2).toString().toInt()
                    val month = book.date.subSequence(3,5).toString().toInt()
                    if ((day-curentDay)<=5 && (month-curentMonth)==0){
                        result.add(book)
                }
            }
        }

        return result
    }
    override fun gerRecommendBooks(): List<BookData> {
    val result = arrayListOf<BookData>()
        val lst = getFullBookList()
        lst.sortedBy {
            it.id
        }

    for (i in 0 until lst.size/4){
        result.add(lst[i])
    }
    return result
    }

    override fun getFullBookList(): List<BookData> {
        val result = arrayListOf<BookData>()
        dao.getBooks().forEach {
            result.add(it.toBookData())
        }
        return result
    }

    override fun getProfilINformation(): Flow<Result<ProfilData>>  = callbackFlow {
        fireStore.collection("profil").document("info").get()
            .addOnSuccessListener {
                val profil = ProfilData(
                    it.get("title") as String,
                    it.get("description") as String,
                    it.get("telegram") as String,
                    it.get("instagram") as String,
                    it.get("image") as String
                )
                trySend(Result.success(profil))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun addComment(message: MessageData):Flow<Result<String>> = callbackFlow {
        val myMessage = hashMapOf(
            "id" to message.id,
            "message" to message.message,
            "name" to message.name
        )
        fireStore.collection("comment").add(myMessage).addOnSuccessListener {
            trySend(Result.success("Xabaringiz jo'natildi..."))
        }.addOnFailureListener {
            trySend(Result.failure(it))
        }
        awaitClose()
    }

    override fun getLikeBooks(like: String): List<BookData> {
        var listt = arrayListOf<BookData>()
        dao.getLikeBooks(like).forEach {
            listt.add(it.toBookData())
        }
        return listt
    }


}