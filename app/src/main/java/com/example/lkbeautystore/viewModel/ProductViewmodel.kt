package com.example.lkbeautystore.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lkbeautystore.Models.EyebrowsModel
import com.example.lkbeautystore.Models.LashesModel
import com.example.lkbeautystore.Models.LipServiceModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class ProductViewmodel: ViewModel() {
    private val dbRef = FirebaseDatabase.getInstance().getReference("eyebrowServices")

    //  Save Eyebrow Service
    fun saveEyebrowService(
        context: Context,
        name: String,
        amount: String,
        description: String,
        imageUri: Uri?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (name.isBlank() || amount.isBlank() || description.isBlank()) {
            onError("Please fill in all fields.")
            return
        }

        viewModelScope.launch {
            try {
                var imageUrl = ""

                if (imageUri != null) {
                    imageUrl = uploadImageToCloudinary(context, imageUri)
                }

                val id = dbRef.push().key ?: return@launch
                val eyebrow = EyebrowsModel(
                    id = id,
                    name = name,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    description = description,
                    imageUrl = imageUrl
                )

                dbRef.child(id).setValue(eyebrow)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onError(it.localizedMessage ?: "Failed to save") }

            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error saving data")
            }
        }
    }

    // ☁️ Upload to Cloudinary and return URL
    private suspend fun uploadImageToCloudinary(context: Context, imageUri: Uri): String {
        return withContext(Dispatchers.IO) {
            val cloudName = "dzw65te0s" //  from cloudinary
            val preset = "product_Img" //
            val url = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"

            // Get actual file path from content Uri
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
            inputStream?.use { input ->
                tempFile.outputStream().use { output -> input.copyTo(output) }
            }

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", tempFile.name, tempFile.asRequestBody("image/*".toMediaType()))
                .addFormDataPart("upload_preset", preset)
                .build()

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val client = OkHttpClient()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val json = JSONObject(response.body?.string() ?: "")
                json.getString("secure_url")
            } else {
                throw Exception("Cloudinary upload failed: ${response.message}")
            }
        }
    }





    // fetch data
    private val _eyebrows = mutableStateListOf<EyebrowsModel>()
    val eyebrows:List<EyebrowsModel> = _eyebrows
//private val dbRef = FirebaseDatabase.getInstance().getReference("eyebrowServices")


    fun fetchEyebrows(context: Context){
        dbRef.get()
            .addOnSuccessListener { snapshot ->
                _eyebrows.clear()
                for (child in snapshot.children) {
                    val eyebrow = child.getValue(EyebrowsModel::class.java)
                    eyebrow?.let {
                        //EyebrowsModel is a data class with val id: String, so id is immutable.

                        // You cannot assign to it. Instead, create a copy:
                        // create a copy with the key as id
                        val eyebrowWithId = it.copy(id = child.key ?: "")
                        _eyebrows.add(eyebrowWithId)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to load services", Toast.LENGTH_LONG).show()
            }
    }


// delete function

    fun deleteEyebrows(id: String, context: Context) {
        val ref = FirebaseDatabase.getInstance().getReference("eyebrowServices").child(id)
        ref.removeValue()
            .addOnSuccessListener {
                //Remove from local list
                _eyebrows.removeAll { it.id == id }
                Toast.makeText(context, "Service deleted successfully", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Service not deleted", Toast.LENGTH_LONG).show()
            }
    }




    fun updateEyebrows( id: String,
                        imageUri: Uri?,
                        name: String,
                        amount: Double,
                        description: String,
                        context: Context,
                        onSuccess: () -> Unit ){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uploadedImageUrl = imageUri?.let { uploadImageToCloudinary(context, it) } ?: ""

                val updateMap = mapOf(
                    "name" to name,
                    "amount" to amount,
                    "description" to description,
                    "imageUrl" to uploadedImageUrl
                )

                val ref = dbRef.child(id)
                ref.updateChildren(updateMap).await()// suspend until done

                fetchEyebrows(context)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Service updated successfully", Toast.LENGTH_LONG).show()
                    onSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Update failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


// lip service starts


    val lipServices = mutableStateListOf<LipServiceModel>()

    // 🔹 Save Lip Service
    fun saveLipService(
        context: Context,
        name: String,
        amount: String,
        description: String,
        imageUri: Uri?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (name.isBlank() || amount.isBlank() || description.isBlank()) {
            onError("Please fill in all fields.")
            return
        }

        val cleanAmount = amount.replace(",", "").trim()
        val parsedAmount = cleanAmount.toDoubleOrNull()
        if (parsedAmount == null) {
            onError("Please enter a valid number for amount (e.g. 10000 or 10,000).")
            return
        }

        viewModelScope.launch {
            try {
                var imageUrl = ""

                //  Upload image to Cloudinary (same method as lip)
                if (imageUri != null) {
                    imageUrl = uploadImageToCloudinary(context, imageUri)
                }

                //  Generate unique ID for this service
                val id = FirebaseDatabase.getInstance().getReference("lipServices").push().key ?: return@launch

                //  Create model object
                val service = LipServiceModel(
                    id = id,
                    name = name,
                    amount = parsedAmount,
                    description = description,
                    imageUrl = imageUrl
                )

                //  Save data to Firebase Realtime Database
                val dbRef = FirebaseDatabase.getInstance().getReference("lipServices").child(id)
                dbRef.setValue(service)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e ->
                        onError(e.localizedMessage ?: "Failed to save Lip Service")
                    }

            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error saving Lip Service")
            }
        }
    }


    // 🔹 Fetch all Lip Services
    fun fetchLipServices(context: Context) {
        val ref = FirebaseDatabase.getInstance().getReference("lipServices")
        ref.get()
            .addOnSuccessListener { snapshot ->
                lipServices.clear()
                snapshot.children.forEach { child ->
                    val service = child.getValue(LipServiceModel::class.java)
                    if (service != null) {
                        child.key?.let { key ->
                            lipServices.add(service.copy(id = key))
                        }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to fetch Lip Services", Toast.LENGTH_SHORT).show()
            }
    }//end of fetch lip service

    // update lip service
    fun updateLipService(
        id: String,
        imageUri: Uri?,
        name: String,
        amount: Double,
        description: String,
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Upload image if a new one is selected
                val uploadedImageUrl = imageUri?.let { uploadImageToCloudinary(context, it) } ?: ""

                val updateMap = mutableMapOf<String, Any>(
                    "name" to name,
                    "amount" to amount,
                    "description" to description
                )

                // Only update imageUrl if a new image was uploaded
                if (uploadedImageUrl.isNotEmpty()) {
                    updateMap["imageUrl"] = uploadedImageUrl
                }

                val ref = FirebaseDatabase.getInstance().getReference("lipServices").child(id)
                ref.updateChildren(updateMap).await() // suspend until done

                // Refresh local list
                fetchLipServices(context)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Lip service updated successfully", Toast.LENGTH_LONG).show()
                    onSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.localizedMessage ?: "Failed to update lip service")
                    Toast.makeText(context, "Update failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    // 🔹 Delete Lip Service
    fun deleteLipService(id: String, context: Context) {
        val ref = FirebaseDatabase.getInstance().getReference("lipServices").child(id)
        ref.removeValue()
            .addOnSuccessListener {
                // Remove from local list
                lipServices.removeAll { it.id == id }
                Toast.makeText(context, "Lip service deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to delete lip service", Toast.LENGTH_SHORT).show()
            }
    }

    // Add Lashes Starts Here
    val lashesServicesl = mutableStateListOf<LashesModel>()

    // save Lashes Service
    fun saveLashesService(
        context: Context,
        name: String,
        amount: String,
        description: String,
        imageUri: Uri?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (name.isBlank() || amount.isBlank() || description.isBlank()) {
            onError("Please fill in all fields.")
            return
        }

        val cleanAmount = amount.replace(",", "").trim()
        val parsedAmount = cleanAmount.toDoubleOrNull()
        if (parsedAmount == null) {
            onError("Please enter a valid number for amount (e.g. 10000 or 10,000).")
            return
        }

        viewModelScope.launch {
            try {
                var imageUrl = ""

                // ✅ Upload image to Cloudinary (same method as Lashes)
                if (imageUri != null) {
                    imageUrl = uploadImageToCloudinary(context, imageUri)
                }

                // ✅ Generate unique ID for this service
                val id = FirebaseDatabase.getInstance().getReference("lashesServices").push().key ?: return@launch

                //  Create model object
                val service = LashesModel(
                    id = id,
                    name = name,
                    amount = parsedAmount,
                    description = description,
                    imageUrl = imageUrl
                )

                //  Save data to Firebase Realtime Database
                val dbRef = FirebaseDatabase.getInstance().getReference("lashesServices").child(id)
                dbRef.setValue(service)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e ->
                        onError(e.localizedMessage ?: "Failed to save Lashes Service")
                    }

            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error saving Lashes Service")
            }
        }
    }// End of save Lashes

    // 🔹 Fetch all Lip Lashes
    fun fetchlashesServices(context: Context) {
        val ref = FirebaseDatabase.getInstance().getReference("lashesServices")
        ref.get()
            .addOnSuccessListener { snapshot ->
                lashesServicesl.clear()
                snapshot.children.forEach { child ->
                    val service = child.getValue(LashesModel::class.java)
                    if (service != null) {
                        child.key?.let { key ->
                            lashesServicesl.add(service.copy(key))


                        }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to fetch Lip Services", Toast.LENGTH_SHORT).show()
            }
    }//end of fetch lip service

    // update lashes service
    fun updateLashesService(
        id: String,
        imageUri: Uri?,
        name: String,
        amount: Double,
        description: String,
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Upload image if a new one is selected
                val uploadedImageUrl = imageUri?.let { uploadImageToCloudinary(context, it) } ?: ""

                val updateMap = mutableMapOf<String, Any>(
                    "name" to name,
                    "amount" to amount,
                    "description" to description
                )

                // Only update imageUrl if a new image was uploaded
                if (uploadedImageUrl.isNotEmpty()) {
                    updateMap["imageUrl"] = uploadedImageUrl
                }

                val ref = FirebaseDatabase.getInstance().getReference("lashesServices").child(id)
                ref.updateChildren(updateMap).await() // suspend until done

                // Refresh local list
                fetchLipServices(context)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Lashes service updated successfully", Toast.LENGTH_LONG).show()
                    onSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.localizedMessage ?: "Failed to update lashes service")
                    Toast.makeText(context, "Update failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    // 🔹 Delete Lashes Service
    fun deleteLashesService(id: String, context: Context) {
        val ref = FirebaseDatabase.getInstance().getReference("lashesServices").child(id)
        ref.removeValue()
            .addOnSuccessListener {
                // Remove from local list
                lashesServicesl.removeAll { it.id == id }
                Toast.makeText(context, "Lashes service deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to delete Lashes service", Toast.LENGTH_SHORT).show()
            }
    }





}


