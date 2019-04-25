package example.chen.com.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.chen.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.chen.answer_is_shown";

    private boolean mAnswerIsTrue;
    private Button showAnswerButton;
    private TextView mAnswerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        showAnswerButton = (Button) findViewById(R.id.show_answer_button);
        showAnswerButton.setOnClickListener(this);

    }

    public static Intent newIntent(Context context, boolean answerIsTrue) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_answer_button:
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
                break;
        }
    }

    private void setAnswerShownResult(boolean answerIsShown) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN,answerIsShown );
        setResult(RESULT_OK, intent);
    }

    public static boolean wasAnswerIsShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false );
    }
}
