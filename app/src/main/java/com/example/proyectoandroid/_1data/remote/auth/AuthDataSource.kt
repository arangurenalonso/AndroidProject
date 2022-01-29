package com.example.proyectoandroid._1data.remote.auth

import android.graphics.Bitmap
import android.net.Uri
import com.example.proyectoandroid._1data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream


class AuthDataSource {

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user

    }

    suspend fun signUp(password: String,nombre: String,apellido: String,telefono: String,email: String): FirebaseUser? {
        val authResult =FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid).set(
                User(
                    nombre = nombre,
                    apellido = apellido,
                    telefono = telefono,
                    email = email,
                    photo_url = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOAAAADgCAMAAAAt85rTAAAAYFBMVEVmZmb///9VVVVeXl5iYmJaWlpcXFzR0dFWVlZwcHD8/PysrKzf39/GxsZsbGzt7e2Ojo719fXn5+d+fn6VlZWlpaWEhITAwMDU1NS4uLjc3NyLi4udnZ1vb293d3fLy8uj0/C3AAAEW0lEQVR4nO2b2XriMAyFwUvIAgFCWAItff+3HJjIIWUoYwcXLHP+yzbhkyL7yLbk0QgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAv4aSidbihNaJVK+2xjdSy3q7q5bjE8tqt61Pf3i1Tf5Qumyq8RVVU+o44qhEPb32rmVaiwhcTBfz2+6dmS/SV9v3ILL8IXpdFEvWc1Gs77t3Zi1ebeVw9KTnSD4pFpkUQmaLYpL3/jHRr7ZzIEr1Zt9sI7rsd8qIYjPrzUTFUmuUWnbBK3Ry5YNKdNGFccnSw4t/W3lTSKTcXjx8tnWPo834XK5+nGN6ZYI4ZzcPhdGX6fXg7KO0ySITZloq15aGdx9izSsfltaB6Twsn2GXL1IaeVOLgSfMs4xWbWpB+pLYPJ2Q3C74SKkgBV1ZmaxWpKRsdEbVlP8stV9TPqy5hJC0P7fWRdmmwz2XZEgSWtg7WLASUtm05jrEQ7dvNDxyoW7PX2ZWEtqStHuLiscYlW04Ng6SoTbtOywiSBqaO4m+yPnoKG2CJg4j9DRGJ7Sx+i2rPJLsHDX0DOnozumrvAjSGLeFFy3uWKiMbpeWmdtbWbt45eCgGCSIJL0clqNikKnD3noJ7+JgvEM0epGJPk1En+ijX6pFv9iOfrv0Bhve2I8soj90GnxsOOUxQt/g4Df6o/uu+JJbhVBTmZdR8SX68ln8BdD4S9i9JoR7TZN8mxB6bSR5nG0k8TcCxd/KFX0z3ij6dspR/A2xFi3Ne94tzSOVJvt7/k0z1o33SizuundmvmZ7RUSJ+s6Nggt5kbKMol79c9vlRxc/+AmNSr6+O1HNisWxPF/OKo+HYnalPVXGbJzqQz/TLbcbreXlyplSUqer7/eZClZBFNue6bOVuHWd7uRk2fQ+w5TPnTuVXAZgXsg7Tdsy7elQnjHxUJXdKnvcpP+ZWyepvTy9YTERVdYZvFcWFqu06V6oGZTPev7VlsdIyWc3TuvgY6jMcdN4PrKeUirtcsohdA+lmVFfTrKfdtuOY9hKYw45x41jWktqo6VBh9BUGcaF8ymu3Jh8GHDGV4cuO7i/LE0M3doXnkqSD5l/3dtmHjq22DwPTVpYDRxk6YPv/zamk2A8WAdN2e0jTCU15j1QBvskJQ2y0GTqupMHrEuagHXGBPAh29J2nWBXOX0upqr72Mc3w2Ad3izUey/Th8ZBgPV65+aY25gQ2vUvPBFTCnt48qTtYmEWmsyQOrg0qN2GGsHcmhWfwNHfZqf9JZdmxSdA333p4btT2T6wxjwqsfiwir5VYI1rdJfg4GFcmc6uoBz0alQaYKJQHx6HFS0ZglrMUBb0k7zkLLxMSLcC/GwCVIB3KEja/TRE0slOUB3qdF/p6OfX2kVDUPeYaAHpqSOS1u0hbev93v4L8C5h9A6OxF/C/DUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADg7fgDyhYmCCEbhAQAAAAASUVORK5CYII="
                )
            ).await()
        }
        return authResult.user

    }
    suspend fun getUserProfile():User {
        val user = FirebaseAuth.getInstance().currentUser
        var usuario = User()
        user?.uid?.let { uid ->
            val get_user_profile =
                FirebaseFirestore.getInstance().collection("users").document(uid).get()
                    .await()

            get_user_profile?.let {
                val usu = User(
                    nombre = it.getString("nombre"),
                    apellido = it.getString("apellido"),
                    telefono = it.getString("telefono"),
                    email = it.getString("email"),
                    is_active = it.getString("is_active").toBoolean(),
                    user_type = it.getDouble("user_type"),
                    date_join = it.getString("date_join"),
                    photo_url = it.getString("photo_url"),
                    direccion = it.getString("direccion"),
                    latitud = it.getDouble("latitud"),
                    longitud = it.getDouble("longitud"),
                )
                usuario = usu
            }
        }
        return usuario
    }
    suspend fun updatePhotoProfile(imageBitmap: Bitmap) {
        val user = FirebaseAuth.getInstance().currentUser
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/profile_picture")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        var downloadUrl = ""
        /*
        * Guarda la foto en Firebase y va a aguardar hasta que la foto se cargue
        *       > imageRef.putBytes(baos.toByteArray()).await()
        * Obtener La URL de la foto que se acaba de subir al servidor
        *       > .storage.downloadUrl.await().toString()
        * */
        downloadUrl =
            imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(downloadUrl))
            .build()


        user?.updateProfile(profileUpdates)?.await()
        user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid).update(
                "photo_url", downloadUrl
            ).await()
        }
    }
    suspend fun update_User_Profile(usuario:User) {

        val user = FirebaseAuth.getInstance().currentUser
        user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid).set(usuario).await()
        }
    }
    suspend fun updateDireccion(direccion: String,latitud:Double,longitud:Double) {

        val user = FirebaseAuth.getInstance().currentUser
        user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid).update(
                "direccion", direccion
            ).await()
        }
    }
}