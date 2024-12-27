package admin_section;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.find_service.AdminReviewAccount;
import com.example.find_service.AdminReviewContactUs;
import com.example.find_service.AdminReviewPost;
import com.example.find_service.MainActivity;
import com.example.find_service.R;

public class AdminHome extends AppCompatActivity {
    Button btnLogout, btnPendingSignUp, btnPendingPosts, btnPendingIssueCU;

    SharedPreferences sharedPreferences;
    int isSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        sharedPreferences = getSharedPreferences("admin", MODE_PRIVATE);
        isSession = sharedPreferences.getInt("admin_session", 0);

        btnLogout = findViewById(R.id.btnLogOut);
        btnPendingSignUp = findViewById(R.id.btnPendingSignedUpUser);
        btnPendingPosts = findViewById(R.id.btnPendingPost);
        btnPendingIssueCU = findViewById(R.id.btnContactUs);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(AdminHome.this, "Logged out", Toast.LENGTH_LONG).show();
                goToHome();
            }
        });

        btnPendingSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AdminReviewAccount.class);
                startActivity(intent);
            }
        });

        btnPendingPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AdminReviewPost.class);
                startActivity(intent);
            }
        });

        btnPendingIssueCU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AdminReviewContactUs.class);
                startActivity(intent);
            }
        });
    }

    public void goToHome(){
        Intent intent = new Intent(AdminHome.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isSession == 0) {
            goToHome();
        }
    }
}
