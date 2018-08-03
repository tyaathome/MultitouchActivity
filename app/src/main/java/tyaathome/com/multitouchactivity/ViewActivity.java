package tyaathome.com.multitouchactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        View one = findViewById(R.id.one);
        View two = findViewById(R.id.two);
        View three = findViewById(R.id.three);
        int position = getIntent().getIntExtra("position", 0);
        switch (position) {
            case 0:
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                break;
            case 1:
                one.setVisibility(View.GONE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.GONE);
                break;
            case 2:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.VISIBLE);
                break;
        }
    }
}
