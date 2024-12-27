package input_form_objects;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDataBaseHelper1 {
    private ObjTableManagement objTableManagement;
    private ObjRegistration objRegistration;

    public int accountAvailable = 0;

    public int checkAccountExistense(String collection, String email){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection).document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()) {
                            accountAvailable = 1;
                            Log.d(TAG, "available " + accountAvailable);
                        }
                    }
                });
        Log.d(TAG, "Returning : " + accountAvailable);
        return accountAvailable;
    }

}
